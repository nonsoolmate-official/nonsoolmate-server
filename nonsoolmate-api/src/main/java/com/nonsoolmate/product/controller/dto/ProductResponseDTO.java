package com.nonsoolmate.product.controller.dto;

import java.util.List;

import com.nonsoolmate.discount.controller.dto.DiscountResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProductResponseDTO", description = "상품 단일 조회 응답 DTO")
public record ProductResponseDTO(
    @Schema(description = "상품 id", example = "1") long productId,
    @Schema(description = "상품 이름", example = "베이직 플랜") String productName,
    @Schema(description = "상품 설명", example = "상품 설명 리스트") List<String> productDescriptions,
    @Schema(description = "상품 원가", example = "100000") long price,
    @Schema(description = "상품 기본 할인 정보") List<DiscountResponseDTO> defaultDiscounts) {
  public static ProductResponseDTO of(
      final Long productId,
      final String productName,
      final List<String> productDescriptions,
      final long price,
      final List<DiscountResponseDTO> defaultDiscounts) {
    return new ProductResponseDTO(
        productId, productName, productDescriptions, price, defaultDiscounts);
  }
}
