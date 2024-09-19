package com.nonsoolmate.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.member.entity.Member;

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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long billingId;

	@NotNull private String billingKey;

	@NotNull
	@OneToOne
	@JoinColumn(name = "customerKey")
	private Member customer;

	@NotNull private String cardNumber;

	@NotNull private String cardCompany;

	private String lastTransactionKey;

	@Builder
	private Billing(
			final String billingKey,
			final Member customer,
			final String cardNumber,
			final String cardCompany) {
		this.billingKey = billingKey;
		this.customer = customer;
		this.cardNumber = cardNumber;
		this.cardCompany = cardCompany;
	}

	public void updateLastTransactionKey(final String lastTransactionKey) {
		this.lastTransactionKey = lastTransactionKey;
	}
}
