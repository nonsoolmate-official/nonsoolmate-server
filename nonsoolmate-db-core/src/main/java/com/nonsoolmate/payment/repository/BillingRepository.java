package com.nonsoolmate.payment.repository;

import static com.nonsoolmate.exception.examRecord.ExamRecordExceptionType.*;
import static com.nonsoolmate.exception.payment.BillingExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nonsoolmate.exception.payment.BillingException;
import com.nonsoolmate.payment.entity.Billing;

public interface BillingRepository extends JpaRepository<Billing, Long> {
	@Query("SELECT b FROM Billing b WHERE b.customer.memberId = :customerId")
	Optional<Billing> findByCustomerId(final @Param("customerId") String customerId);

	default Billing findByCustomerIdOrThrow(final String customerId) {
		return findByCustomerId(customerId).orElseThrow(() -> new BillingException(NOT_FOUND_BILLING));
	}
}
