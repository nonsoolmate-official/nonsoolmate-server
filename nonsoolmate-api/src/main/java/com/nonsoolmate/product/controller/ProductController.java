package com.nonsoolmate.product.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.product.controller.dto.ProductResponseDTO;
import com.nonsoolmate.product.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements ProductApi {
	private final ProductService productService;

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponseDTO> getProduct(
			@PathVariable("productId") final Long productId, @AuthMember final String memberId) {
		ProductResponseDTO response = productService.getProduct(productId, memberId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("")
	public ResponseEntity<List<ProductResponseDTO>> getProducts() {
		List<ProductResponseDTO> response = productService.getProducts();
		return ResponseEntity.ok(response);
	}
}
