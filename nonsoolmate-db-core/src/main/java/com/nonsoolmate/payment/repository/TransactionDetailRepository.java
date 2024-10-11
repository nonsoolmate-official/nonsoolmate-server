package com.nonsoolmate.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.payment.entity.TransactionDetail;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {
  boolean existsByCustomerKey(final String customerKey);
}
