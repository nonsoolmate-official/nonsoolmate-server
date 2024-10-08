package com.nonsoolmate.product.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.discount.controller.dto.DiscountResponseDTO;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.discountProduct.service.DiscountProductService;
import com.nonsoolmate.product.controller.dto.ProductResponseDTO;
import com.nonsoolmate.product.entity.Product;
import com.nonsoolmate.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
	private final ProductRepository productRepository;
	private final DiscountProductService productProductService;

	public ProductResponseDTO getProduct(final Long productId, final String memberId) {
		Product product = productRepository.findByProductIdOrThrow(productId);
		List<DiscountProduct> defaultDiscountProducts =
				productProductService.getDefaultDiscountProductsByProduct(product, memberId);
		List<DiscountResponseDTO> defaultDiscounts =
				defaultDiscountProducts.stream()
						.map(
								discountProduct ->
										DiscountResponseDTO.of(
												discountProduct.getDiscount().getDiscountId(),
												discountProduct.getDiscount().getDiscountName(),
												discountProduct.getDiscount().getDiscountRate()))
						.toList();
		return ProductResponseDTO.of(
				product.getProductId(), product.getProductName(), product.getPrice(), defaultDiscounts);
	}
}
