package com.nonsoolmate.payment.repository;

import static com.nonsoolmate.exception.payment.BillingExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.exception.payment.BillingException;
import com.nonsoolmate.payment.entity.Billing;

public interface BillingRepository extends JpaRepository<Billing, Long> {
	Optional<Billing> findByCustomerMemberId(final String customerId);

	default Billing findByCustomerIdOrThrow(final String customerId) {
		return findByCustomerMemberId(customerId)
				.orElseThrow(() -> new BillingException(NOT_FOUND_BILLING));
	}
}
