package com.nonsoolmate.payment.controller;

import org.springframework.http.ResponseEntity;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.payment.controller.dto.response.CustomerInfoDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "payment", description = "결제와 관련된 API")
public interface PaymentApi {
	@ApiResponses(value = {@ApiResponse(responseCode = "200")})
	@Operation(
			summary = "결제 페이지: customer 정보 조회",
			description = "결제 위젯 호출을 위해 customer의 customerKey, name, email을 조회합니다.")
	ResponseEntity<CustomerInfoDTO> getCustomerInfo(
			@Parameter(hidden = true) @AuthMember String memberId);
}
