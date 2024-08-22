package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "RegisterCouponRequestDTO", description = "쿠폰 등록 요청 DTO")
public record RegisterCouponRequestDTO(
	@NotNull
	@Schema(description = "쿠폰 번호", example = "1234-5678-abcd")
	String couponNumber
) {
}
