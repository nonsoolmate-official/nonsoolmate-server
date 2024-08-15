package com.nonsoolmate.nonsoolmateServer.domain.payment.entity;

import java.time.LocalDateTime;

import com.nonsoolmate.nonsoolmateServer.domain.order.entity.Order;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
		@UniqueConstraint(name = "UK_TRANSACTION_KEY_CUSTOMER_KEY_KEY", columnNames = {"transactionKey", "customerKey"})
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransactionDetail {
	@Id
	private String transactionKey;

	@NotNull
	private String paymentKey;

	@OneToOne(mappedBy = "transactionDetail")
	private Order order;

	// TODO: customerKey -> memberTable의 memberId를 String으로 변환해서 사용할지 고민
	// MEMBER <-> BILLING 1:1, MEMBER<->TRANSACTION 1:N으로 관리하는게 어떤지
	@NotNull
	private String customerKey;

	@NotNull
	private String receiptUrl;

	@NotNull
	LocalDateTime transactionAt;

	@Builder
	public TransactionDetail(final String transactionKey, final String paymentKey, final String customerKey,
		final String receiptUrl, final LocalDateTime transactionAt) {
		this.transactionKey = transactionKey;
		this.paymentKey = paymentKey;
		this.customerKey = customerKey;
		this.receiptUrl = receiptUrl;
		this.transactionAt = transactionAt;
	}
}
