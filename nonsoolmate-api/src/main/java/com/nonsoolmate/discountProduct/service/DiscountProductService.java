package com.nonsoolmate.discountProduct.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.discount.entity.Discount;
import com.nonsoolmate.discountProduct.DiscountProductRepository;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.payment.repository.TransactionDetailRepository;
import com.nonsoolmate.product.entity.Product;
import com.nonsoolmate.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiscountProductService {
	private final TransactionDetailRepository transactionDetailRepository;
	private final DiscountProductRepository discountProductRepository;
	private final ProductRepository productRepository;

	private static final double NOT_FIRST_PURCHASE_DISCOUNT_RATE = 0.0;

	public long getDiscountedProductPrice(final Long productId, final String memberId) {
		Product product = productRepository.findByProductIdOrThrow(productId);
		double discountedPrice = product.getPrice();

		List<DiscountProduct> discountProducts = discountProductRepository.findAllByProductId(product);
		boolean isFirstPurchaseMember = isFirstPurchase(memberId);

		for (DiscountProduct discountProduct : discountProducts) {
			Discount discount = discountProduct.getDiscount();
			double discountPercent = getDiscountPercent(discount, isFirstPurchaseMember);
			discountedPrice *= (1.0 - discountPercent);
		}

		return Math.round(discountedPrice);
	}

	private double getDiscountPercent(final Discount discount, final boolean isFirstPurchaseMember) {
		switch (discount.getDiscountType()) {
			case FIRST_PURCHASE:
				return isFirstPurchaseMember
						? discount.getDiscountRate()
						: NOT_FIRST_PURCHASE_DISCOUNT_RATE;
			default:
				return discount.getDiscountRate();
		}
	}

	private boolean isFirstPurchase(final String memberId) {
		return !transactionDetailRepository.existsByCustomerKey(memberId);
	}
}
