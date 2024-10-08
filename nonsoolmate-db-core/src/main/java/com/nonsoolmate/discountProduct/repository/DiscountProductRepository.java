package com.nonsoolmate.discountProduct.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.product.entity.Product;

public interface DiscountProductRepository
		extends JpaRepository<DiscountProduct, Long>, DiscountProductCustomRepository {
	@Query("select dp from DiscountProduct dp where dp.product = :product")
	List<DiscountProduct> findAllByProductId(@Param("product") final Product product);
}
