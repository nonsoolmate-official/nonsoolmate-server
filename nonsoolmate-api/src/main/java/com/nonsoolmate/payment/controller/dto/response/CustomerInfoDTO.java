package com.nonsoolmate.payment.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CustomerInfoDTO", description = "결제 위젯 호출을 위한 고객 정보 응답 DTO")
public record CustomerInfoDTO(
	@Schema(description = "위젯 호출 시 필요한 customerKey", example = "edslskdkdksl") String customerKey,
	@Schema(description = "회원의 이름", example = "김성은") String customerName,
	@Schema(description = "회원의 email", example = "example@gmail.com") String customerEmail) {
	public static CustomerInfoDTO of(final String customerKey, final String customerName,
		final String customerEmail) {
		return new CustomerInfoDTO(customerKey, customerName, customerEmail);
	}
}
