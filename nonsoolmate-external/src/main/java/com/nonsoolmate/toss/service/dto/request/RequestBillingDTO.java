package com.nonsoolmate.toss.service.dto.request;

public record RequestBillingDTO(String customerKey, long amount, String orderId, String orderName) {
  public static RequestBillingDTO of(
      final String customerKey, final long amount, final String orderId, final String orderName) {
    return new RequestBillingDTO(customerKey, amount, orderId, orderName);
  }
}
