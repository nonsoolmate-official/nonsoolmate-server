package com.nonsoolmate.nonsoolmateServer.domain.order.entity;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDetail {
	@Id
	private String orderId;

	@NotNull
	private String orderName;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "customer_key")
	private Member member;

	@NotNull
	private long amount;

	@Builder
	public OrderDetail(final String orderId, final String orderName, final Member member, final long amount) {
		this.orderId = orderId;
		this.orderName = orderName;
		this.member = member;
		this.amount = amount;
	}
}
