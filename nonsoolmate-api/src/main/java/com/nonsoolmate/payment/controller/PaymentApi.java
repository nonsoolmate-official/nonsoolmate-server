package com.nonsoolmate.payment.controller;

import org.springframework.http.ResponseEntity;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.payment.controller.dto.request.CreatePaymentRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.CustomerInfoDTO;
import com.nonsoolmate.payment.controller.dto.response.PaymentResponseDTO;
import com.nonsoolmate.response.ErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "201", description = "결제에 성공한 경우"),
				@ApiResponse(
						responseCode = "400",
						description = "사용자가 사용할 수 있는 couponMemberId를 보내지 않은 경우",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
				@ApiResponse(
						responseCode = "400",
						description = "유효하지 않은 productId(상품 id)를 보낸 경우",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
				@ApiResponse(
						responseCode = "400",
						description = "이미 멤버십을 구독하고 있는 경우",
						content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
			})
	@Operation(summary = "결제 페이지: 멤버십 상품 결제", description = "결제 페이지: 멤버십 상품을 결제하는 경우")
	ResponseEntity<PaymentResponseDTO> createMembershipPayment(
			CreatePaymentRequestDTO paymentRequestDTO, @Parameter(hidden = true) String memberId);
}
