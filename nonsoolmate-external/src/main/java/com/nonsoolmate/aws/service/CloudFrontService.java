package com.nonsoolmate.aws.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nonsoolmate.aws.service.vo.PreSignedUrlVO;
import com.nonsoolmate.exception.aws.AWSBusinessException;
import com.nonsoolmate.exception.aws.AWSExceptionType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.cloudfront.CloudFrontUtilities;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;

@Component
@RequiredArgsConstructor
@Slf4j
public class CloudFrontService {
	private static final Long PRE_SIGNED_URL_EXPIRE_SECONDS = 30L;

	@Value("${aws-property.distribution-domain}")
	private String distributionDomain;

	@Value("${aws-property.private-key-file-path}")
	private String privateKeyFilePath;

	@Value("${aws-property.key-pair-id}")
	private String keyPairId;

	public PreSignedUrlVO createPreSignedPutUrl(String path) {
		String fileName = generateZipFileName();
		return PreSignedUrlVO.of(fileName, createPreSignedUrl(path + fileName));
	}

	private String generateZipFileName() {
		return UUID.randomUUID() + ".zip";
	}

	public String createPreSignedGetUrl(String path, String fileName) {
		String resourcePath = getEncodedResourcePath(path, fileName);
		return createPreSignedUrl(resourcePath);
	}

	private String createPreSignedUrl(String resourcePath) {
		try {
			String cloudFrontUrl = "https://" + distributionDomain + "/" + resourcePath;
			Instant expirationTime =
					Instant.now().plus(PRE_SIGNED_URL_EXPIRE_SECONDS, ChronoUnit.SECONDS);
			Path keyPath = Paths.get(privateKeyFilePath);

			CloudFrontUtilities cloudFrontUtilities = CloudFrontUtilities.create();
			CannedSignerRequest cannedSignerRequest =
					CannedSignerRequest.builder()
							.resourceUrl(cloudFrontUrl)
							.privateKey(keyPath)
							.keyPairId(keyPairId)
							.expirationDate(expirationTime)
							.build();
			SignedUrl signedUrl = cloudFrontUtilities.getSignedUrlWithCannedPolicy(cannedSignerRequest);
			return signedUrl.url();
		} catch (AWSBusinessException e) {
			throw new AWSBusinessException(AWSExceptionType.GET_PRESIGNED_URL_AWS_CLOUDFRONT_FAIL);
		} catch (Exception e) {
			throw new AWSBusinessException(AWSExceptionType.NOT_FOUND_AWS_PRIVATE_KEY);
		}
	}

	private String getEncodedResourcePath(String path, String fileName) {
		try {
			String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
			return path + encodedFileName;
		} catch (UnsupportedEncodingException e) {
			throw new AWSBusinessException(AWSExceptionType.FAIL_ENCODING_FILE_NAME);
		}
	}
}
