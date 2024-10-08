package com.nonsoolmate.product.controller;

import org.springframework.http.ResponseEntity;

import com.nonsoolmate.product.controller.dto.ProductResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product", description = "상품과 관련된 API")
public interface ProductApi {
	@ApiResponses(
			value = {
				@ApiResponse(responseCode = "200", description = "상품 단일 조회에 성공했습니다."),
				@ApiResponse(responseCode = "404", description = "해당하는 상품이 존재하지 않습니다.")
			})
	@Operation(summary = "결제 페이지: 상품 단일 조회(가격, 할인 정보)", description = "상품 단일 정보에 대해 조회하는 경우")
	ResponseEntity<ProductResponseDTO> getProduct(
			@Schema(description = "상품 id") Long productId, @Schema(hidden = true) String memberId);
}
