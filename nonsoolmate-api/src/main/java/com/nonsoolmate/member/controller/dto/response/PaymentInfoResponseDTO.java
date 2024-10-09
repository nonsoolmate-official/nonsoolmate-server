package com.nonsoolmate.member.controller.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.nonsoolmate.discount.controller.dto.DiscountResponseDTO;
import com.nonsoolmate.payment.entity.Billing;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PaymentInfoResponseDTO", description = "다음달 결제 정보 DTO")
public record PaymentInfoResponseDTO(
		@Schema(description = "다음 결제일", example = "류가은") LocalDateTime nextPaymentDate,
		@Schema(description = "결제 수단", example = "카카오뱅크") String paymentMethod,
		@Schema(description = "카드 번호", example = "53651045****331*") String cardNumber,
		@Schema(description = "쿠폰 정보", example = "") GetUsedCouponResponseDTO coupon,
		@Schema(description = "할인 이벤트", example = "") List<DiscountResponseDTO> discountEvent,
		@Schema(description = "총 할인가", example = "30000") long totalDiscountPrice,
		@Schema(description = "결제 예정 금액", example = "90000") long totalPrice) {
	public static PaymentInfoResponseDTO of(
			LocalDateTime nextPaymentDate,
			Billing billing,
			GetUsedCouponResponseDTO coupon,
			List<DiscountResponseDTO> discountResponseDTOs,
			long totalDiscountPrice,
			long totalPrice) {

		return new PaymentInfoResponseDTO(
				nextPaymentDate,
				billing.getCardCompany(),
				billing.getCardNumber(),
				coupon,
				discountResponseDTOs,
				totalDiscountPrice,
				totalPrice);
	}
}
