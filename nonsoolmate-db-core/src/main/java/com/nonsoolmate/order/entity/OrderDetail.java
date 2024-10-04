package com.nonsoolmate.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.common.BaseTimeEntity;
import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.product.entity.Product;

import io.hypersistence.utils.hibernate.id.Tsid;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrderDetail extends BaseTimeEntity {
	@Id @Tsid private String orderId;

	@NotNull private String orderName;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "customer_key")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coupon_member_id")
	private CouponMember couponMember;

	@Min(0)
	private long amount;

	@Builder
	private OrderDetail(
			final String orderName,
			final Member member,
			final Product product,
			final CouponMember couponMember,
			final long amount) {
		this.orderName = orderName;
		this.member = member;
		this.product = product;
		this.couponMember = couponMember;
		this.amount = amount;
	}
}
