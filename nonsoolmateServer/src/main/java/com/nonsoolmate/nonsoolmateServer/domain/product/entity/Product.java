package com.nonsoolmate.nonsoolmateServer.domain.product.entity;

import jakarta.persistence.Entity;
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
	private Long productId;

	@NotNull
	private String productName;

	@Min(0)
	private long price;
}
