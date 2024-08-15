package com.nonsoolmate.nonsoolmateServer.domain.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// TODO: Order가 customerKey를 가진다면, Transaction은 가지지 않아도 되지 않는가에 대하여
public class OrderDetail {
	@Id
	private String orderId;

	@NotNull
	private String orderName;

	@NotNull
	private long amount;

	@Builder
	public OrderDetail(final String orderId, final String orderName, final long amount) {
		this.orderId = orderId;
		this.orderName = orderName;
		this.amount = amount;
	}
}
