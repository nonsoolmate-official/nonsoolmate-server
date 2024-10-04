package com.nonsoolmate.product.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@NotNull private String productName;

	@Min(0)
	private long price;

	@NotNull private String description;

	@NotNull private LocalDateTime startDate;

	private LocalDateTime endDate;

	public List<String> getDescriptions() {
		return Arrays.asList(description.split(","));
	}
}
