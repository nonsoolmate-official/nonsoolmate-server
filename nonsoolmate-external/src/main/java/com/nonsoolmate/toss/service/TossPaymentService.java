package com.nonsoolmate.toss.service;

import static com.nonsoolmate.exception.common.CommonErrorType.*;
import static com.nonsoolmate.exception.payment.BillingExceptionType.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.nonsoolmate.exception.common.BusinessException;
import com.nonsoolmate.exception.payment.BillingException;
import com.nonsoolmate.toss.service.dto.request.IssueBillingDTO;
import com.nonsoolmate.toss.service.dto.response.TossPaymentBillingDTO;
import com.nonsoolmate.toss.service.vo.TossPaymentBillingVO;

import reactor.core.publisher.Mono;

@Service
public class TossPaymentService {
	private static final String TOSS_AUTHORIZATION_HEADER = "Authorization";
	private static final String TOSS_AUTHORIZATION_PREFIX = "Basic ";
	private static final String TOSS_ISSUE_BILLING_URI =
			"https://api.tosspayments.com/v1/billing/authorizations/issue";
	private final String secretKey;

	public TossPaymentService(@Value("${toss.billing.api-key}") String apiKey) {
		this.secretKey = getSecretKey(apiKey);
	}

	private String getSecretKey(String apiKey) {
		return Base64.getEncoder().encodeToString(apiKey.getBytes(StandardCharsets.UTF_8));
	}

	public TossPaymentBillingVO issueBilling(final String customerKey, final String authKey) {
		WebClient webClient = WebClient.builder().build();
		IssueBillingDTO issueBillingDTO = IssueBillingDTO.of(authKey, customerKey);

		try {
			TossPaymentBillingDTO tossPaymentBillingDTO =
					webClient
							.post()
							.uri(TOSS_ISSUE_BILLING_URI)
							.header(TOSS_AUTHORIZATION_HEADER, TOSS_AUTHORIZATION_PREFIX + secretKey)
							.header("Content-Type", "application/json")
							.body(Mono.just(issueBillingDTO), IssueBillingDTO.class)
							.retrieve()
							.bodyToMono(TossPaymentBillingDTO.class)
							.block();

			return TossPaymentBillingVO.of(
					tossPaymentBillingDTO.customerKey(),
					tossPaymentBillingDTO.billingKey(),
					tossPaymentBillingDTO.cardCompany(),
					tossPaymentBillingDTO.cardNumber());
		} catch (WebClientResponseException e) {
			boolean isUnauthorized = e.getStatusCode().equals(HttpStatus.UNAUTHORIZED);
			boolean isNotFound = e.getStatusCode().equals(HttpStatus.NOT_FOUND);
			if (isUnauthorized) {
				throw new BusinessException(UNAUTHORIZED);
			} else if (isNotFound) {
				throw new BillingException(TOSS_PAYMENT_ISSUE_BILLING);
			} else {
				throw new BusinessException(EXTERNAL_SERVER_ERROR);
			}
		}
	}
}
