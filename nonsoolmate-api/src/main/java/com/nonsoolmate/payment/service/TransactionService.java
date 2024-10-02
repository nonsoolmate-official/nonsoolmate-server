package com.nonsoolmate.payment.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.payment.entity.TransactionDetail;
import com.nonsoolmate.payment.repository.TransactionDetailRepository;
import com.nonsoolmate.payment.service.vo.TransactionVO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {
	private final TransactionDetailRepository transactionRepository;

	@Transactional
	public TransactionDetail createTransaction(final TransactionVO transactionVO) {
		TransactionDetail transaction =
				TransactionDetail.builder()
						.transactionKey(transactionVO.transactionKey())
						.paymentKey(transactionVO.paymentKey())
						.customerKey(transactionVO.customerKey())
						.order(transactionVO.order())
						.transactionAt(transactionVO.transactionAt())
						.build();
		return transactionRepository.save(transaction);
	}

	public boolean isFirstPurchase(final String memberId) {
		return !transactionRepository.existsByCustomerKey(memberId);
	}
}
