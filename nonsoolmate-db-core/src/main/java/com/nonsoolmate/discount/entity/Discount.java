package com.nonsoolmate.discount.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.discount.entity.enums.DiscountType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long discountId;

	@NotNull private String discountName;

	private double discountRate;

	@Enumerated(EnumType.STRING)
	private DiscountType discountType;

	@NotNull private String description;
}
