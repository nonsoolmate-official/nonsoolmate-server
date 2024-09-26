package com.nonsoolmate.toss.service;

import static com.nonsoolmate.exception.payment.BillingExceptionType.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nonsoolmate.exception.common.BusinessException;
import com.nonsoolmate.toss.service.dto.request.IssueBillingDTO;
import com.nonsoolmate.toss.service.dto.response.TossPaymentBillingDTO;
import com.nonsoolmate.toss.service.vo.TossPaymentBillingVO;

@Service
public class TossPaymentService {
	private static final String TOSS_AUTHORIZATION_HEADER = "Authorization";
	private static final String TOSS_AUTHORIZATION_PREFIX = "Basic";
	private static final String TOSS_ISSUE_BILLING_URI =
			"https://api.tosspayments.com/v1/billing/authorizations/issue";

	@Value("${toss.billing.api-key}")
	private String accessToken;

	public TossPaymentBillingVO issueBilling(final String customerKey, final String authKey) {
		WebClient webClient = WebClient.builder().build();
		IssueBillingDTO issueBillingDTO = IssueBillingDTO.of(authKey, customerKey);

		try {
			TossPaymentBillingDTO tossPaymentBillingDTO =
					webClient
							.post()
							.uri(TOSS_ISSUE_BILLING_URI)
							.header(TOSS_AUTHORIZATION_HEADER, TOSS_AUTHORIZATION_PREFIX + accessToken)
							.header("Content-Type", "application/json")
							.body(issueBillingDTO, IssueBillingDTO.class)
							.retrieve()
							.bodyToMono(TossPaymentBillingDTO.class)
							.block();

			return TossPaymentBillingVO.of(
					tossPaymentBillingDTO.customerKey(),
					tossPaymentBillingDTO.billingKey(),
					tossPaymentBillingDTO.cardCompany(),
					tossPaymentBillingDTO.cardNumber());
		} catch (RuntimeException e) {
			throw new BusinessException(TOSS_PAYMENT_ISSUE_BILLING);
		}
	}
}
