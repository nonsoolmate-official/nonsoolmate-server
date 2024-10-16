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
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.toss.service.dto.request.IssueBillingKeyDTO;
import com.nonsoolmate.toss.service.dto.request.RequestBillingDTO;
import com.nonsoolmate.toss.service.dto.response.TossPaymentBillingDTO;
import com.nonsoolmate.toss.service.dto.response.TossPaymentTransactionDTO;
import com.nonsoolmate.toss.service.vo.TossPaymentBillingKeyVO;
import com.nonsoolmate.toss.service.vo.TossPaymentTransactionVO;

import reactor.core.publisher.Mono;

@Service
public class TossPaymentService {
  private static final String TOSS_AUTHORIZATION_HEADER = "Authorization";
  private static final String TOSS_AUTHORIZATION_PREFIX = "Basic ";
  private static final String TOSS_ISSUE_BILLING_URI =
      "https://api.tosspayments.com/v1/billing/authorizations/issue";
  private static final String TOSS_REQUEST_BILLING_URI = "https://api.tosspayments.com/v1/billing/";
  private final String secretKey;

  public TossPaymentService(@Value("${toss.billing.api-key}") String apiKey) {
    this.secretKey = getSecretKey(apiKey);
  }

  private String getSecretKey(String apiKey) {
    return Base64.getEncoder().encodeToString(apiKey.getBytes(StandardCharsets.UTF_8));
  }

  public TossPaymentBillingKeyVO issueBillingKey(final String customerKey, final String authKey) {
    IssueBillingKeyDTO issueBillingKeyDTO = IssueBillingKeyDTO.of(authKey, customerKey);
    TossPaymentBillingDTO tossPaymentBillingDTO = getTossPaymentBillingKeyDTO(issueBillingKeyDTO);

    return TossPaymentBillingKeyVO.of(
        tossPaymentBillingDTO.customerKey(),
        tossPaymentBillingDTO.billingKey(),
        tossPaymentBillingDTO.cardCompany(),
        tossPaymentBillingDTO.cardNumber());
  }

  private TossPaymentBillingDTO getTossPaymentBillingKeyDTO(IssueBillingKeyDTO request) {
    WebClient webClient = WebClient.builder().build();
    try {
      return webClient
          .post()
          .uri(TOSS_ISSUE_BILLING_URI)
          .header(TOSS_AUTHORIZATION_HEADER, TOSS_AUTHORIZATION_PREFIX + secretKey)
          .header("Content-Type", "application/json")
          .body(Mono.just(request), IssueBillingKeyDTO.class)
          .retrieve()
          .bodyToMono(TossPaymentBillingDTO.class)
          .block();
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

  public TossPaymentTransactionVO requestBilling(
      final String billingKey, final String customerKey, final OrderDetail order) {
    RequestBillingDTO request =
        RequestBillingDTO.of(
            customerKey, order.getAmount(), order.getOrderId(), order.getOrderName());
    TossPaymentTransactionDTO tossPaymentTransactionDTO =
        getTossPaymentTransactionDTO(billingKey, request);
    return TossPaymentTransactionVO.fromDTO(tossPaymentTransactionDTO);
  }

  private TossPaymentTransactionDTO getTossPaymentTransactionDTO(
      String billingKey, RequestBillingDTO request) {

    WebClient webClient = WebClient.builder().build();
    try {
      return webClient
          .post()
          .uri(TOSS_REQUEST_BILLING_URI + billingKey)
          .header(TOSS_AUTHORIZATION_HEADER, TOSS_AUTHORIZATION_PREFIX + secretKey)
          .header("Content-Type", "application/json")
          .body(Mono.just(request), RequestBillingDTO.class)
          .retrieve()
          .bodyToMono(TossPaymentTransactionDTO.class)
          .block();
    } catch (WebClientResponseException e) {
      throw new BusinessException(EXTERNAL_SERVER_ERROR);
    }
  }
}
