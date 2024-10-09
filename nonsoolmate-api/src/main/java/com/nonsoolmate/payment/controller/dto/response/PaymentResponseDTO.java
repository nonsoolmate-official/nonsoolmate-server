package com.nonsoolmate.payment.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PaymentResponseDTO", description = "결제 요청에 대한 응답 DTO")
public record PaymentResponseDTO(
    /**
     * @note: paymentId = Transaction_Detail.transactionKey
     */
    @Schema(description = "결제 완료 이후 반환되는 paymentId", example = "edslskdkdksl") String paymentId) {
  public static PaymentResponseDTO of(String paymentId) {
    return new PaymentResponseDTO(paymentId);
  }
}
