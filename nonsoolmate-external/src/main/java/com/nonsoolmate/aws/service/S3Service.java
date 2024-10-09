package com.nonsoolmate.aws.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nonsoolmate.aws.config.AWSConfig;
import com.nonsoolmate.exception.aws.AWSBusinessException;
import com.nonsoolmate.exception.aws.AWSClientException;
import com.nonsoolmate.exception.aws.AWSExceptionType;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {
  private final AWSConfig awsConfig;

  @Value("${aws-property.s3-bucket-name}")
  private String bucketName;

  public String validateURL(final String prefix, final String fileName) {
    try {
      String zipUrl = prefix + fileName;
      S3Client s3Client = awsConfig.getS3Client();

      HeadObjectRequest request =
          HeadObjectRequest.builder().bucket(bucketName).key(zipUrl).build();
      s3Client.headObject(request);
      return fileName;
    } catch (S3Exception e) {
      throw new AWSClientException(AWSExceptionType.NOT_FOUND_SHEET_FILE_AWS_S3);
    }
  }

  public void deleteFile(final String prefix, final String fileName) {
    String key = prefix + fileName;
    final S3Client s3Client = awsConfig.getS3Client();
    try {
      s3Client.deleteObject(
          (DeleteObjectRequest.Builder builder) -> builder.bucket(bucketName).key(key).build());
    } catch (S3Exception e) {
      throw new AWSBusinessException(AWSExceptionType.DELETE_FILE_AWS_S3_FAIL);
    }
  }
}
