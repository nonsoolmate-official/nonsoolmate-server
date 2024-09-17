package com.nonsoolmate.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import com.nonsoolmate.member.entity.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
		uniqueConstraints = {
			@UniqueConstraint(
					name = "UK_BILLING_KEY_CUSTOMER_KEY_KEY",
					columnNames = {"billingKey", "customerKey"})
		})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Billing {
	@Id private String billingKey;

	@NotNull
	@OneToOne
	@JoinColumn(name = "customerKey")
	private Member customer;

	@NotNull private String cardNumber;

	private String lastTransactionKey;

	@Builder
	public Billing(final String billingKey, final Member customer, final String cardNumber) {
		this.billingKey = billingKey;
		this.customer = customer;
		this.cardNumber = cardNumber;
	}

	public void updateLastTransactionKey(final String lastTransactionKey) {
		this.lastTransactionKey = lastTransactionKey;
	}
}
