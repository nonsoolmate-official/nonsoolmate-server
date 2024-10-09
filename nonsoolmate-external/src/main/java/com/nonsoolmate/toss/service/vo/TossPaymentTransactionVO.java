package com.nonsoolmate.toss.service.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nonsoolmate.toss.service.dto.response.TossPaymentTransactionDTO;

public record TossPaymentTransactionVO(
    String transactionKey, String paymentKey, LocalDateTime transactionAt, String receiptUrl) {
  public static TossPaymentTransactionVO fromDTO(TossPaymentTransactionDTO dto) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime transformedTransactionAt =
        LocalDateTime.parse(dto.approvedAt().substring(0, 19), formatter);
    return new TossPaymentTransactionVO(
        dto.lastTransactionKey(), dto.paymentKey(), transformedTransactionAt, dto.receipt().url());
  }
}
