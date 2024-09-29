package com.nonsoolmate.discountProduct.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.discount.entity.Discount;
import com.nonsoolmate.product.entity.Product;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscountProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long discountProductId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	private Discount discount;

	@NotNull LocalDateTime startDate;
	LocalDateTime endDate;
}
