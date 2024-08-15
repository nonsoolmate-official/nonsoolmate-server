package com.nonsoolmate.nonsoolmateServer.domain.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	uniqueConstraints = {
		@UniqueConstraint(name = "UK_BILLING_KEY_CUSTOMER_KEY_KEY", columnNames = {"billingKey", "customerKey"})
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class billing {
	@Id
	private String billingKey;
	@NotNull
	private String customerKey;
	@NotNull
	private String cardNumber;
	private String lastTransactionKey;

	@Builder
	public billing(final String billingKey, final String customerKey, final String cardNumber) {
		this.billingKey = billingKey;
		this.customerKey = customerKey;
		this.cardNumber = cardNumber;
	}

	public void updateLastTransactionKey(final String lastTransactionKey) {
		this.lastTransactionKey = lastTransactionKey;
	}
}
