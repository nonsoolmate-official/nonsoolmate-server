package com.nonsoolmate.payment.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.common.BaseTimeEntity;
import com.nonsoolmate.order.entity.OrderDetail;

@Entity
@Table(
    uniqueConstraints = {
      @UniqueConstraint(
          name = "UK_TRANSACTION_KEY_ORDER_ID",
          columnNames = {"transactionKey", "order_id"})
    })
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TransactionDetail extends BaseTimeEntity {
  @Id private String transactionKey;

  @NotNull private String paymentKey;

  @NotNull private String customerKey;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private OrderDetail order;

  @NotNull private String receiptUrl;

  @NotNull LocalDateTime transactionAt;

  @Builder
  private TransactionDetail(
      final String transactionKey,
      final String paymentKey,
      final String customerKey,
      final OrderDetail order,
      final String receiptUrl,
      final LocalDateTime transactionAt) {
    this.transactionKey = transactionKey;
    this.paymentKey = paymentKey;
    this.customerKey = customerKey;
    this.order = order;
    this.receiptUrl = receiptUrl;
    this.transactionAt = transactionAt;
  }
}
