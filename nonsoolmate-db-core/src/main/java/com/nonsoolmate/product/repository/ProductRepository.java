package com.nonsoolmate.product.repository;

import static com.nonsoolmate.exception.product.ProductExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.exception.product.ProductException;
import com.nonsoolmate.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	Optional<Product> findByProductId(Long productId);

	default Product findByProductIdOrThrow(Long productId) {
		return findByProductId(productId).orElseThrow(() -> new ProductException(INVALID_PRODUCT_ID));
	}
}
