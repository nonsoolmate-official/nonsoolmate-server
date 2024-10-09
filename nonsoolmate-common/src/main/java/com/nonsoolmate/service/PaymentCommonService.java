package com.nonsoolmate.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.discount.entity.Discount;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.discountProduct.repository.DiscountProductRepository;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.order.entity.OrderDetail;
import com.nonsoolmate.order.repository.OrderRepository;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;
import com.nonsoolmate.payment.repository.TransactionDetailRepository;
import com.nonsoolmate.product.entity.Product;
import com.nonsoolmate.product.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentCommonService {
  private static final double NOT_FIRST_PURCHASE_DISCOUNT_RATE = 0.0;

  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final DiscountProductRepository discountProductRepository;
  private final BillingRepository billingRepository;
  private final TransactionDetailRepository transactionRepository;

  public String getBillingKey(final String memberId) {
    Billing billing = billingRepository.findByCustomerIdOrThrow(memberId);
    return billing.getBillingKey();
  }

  @Transactional
  public void createOrder(final Product product, final Long couponMemberId, final String memberId) {
    Member member = memberRepository.findByMemberIdOrThrow(memberId);
    long discountedProductPrice = getDiscountedProductPrice(product.getProductId(), memberId);

    OrderDetail order =
        OrderDetail.builder()
            .orderName(product.getProductName())
            .member(member)
            .product(product)
            .amount(discountedProductPrice)
            .build();

    orderRepository.save(order);
  }

  private long getDiscountedProductPrice(final Long productId, final String memberId) {
    Product product = productRepository.findByProductIdOrThrow(productId);
    double discountedPrice = product.getPrice();

    List<DiscountProduct> discountProducts = discountProductRepository.findAllByProduct(product);
    boolean isFirstPurchaseMember = isFirstPurchase(memberId);

    for (DiscountProduct discountProduct : discountProducts) {
      Discount discount = discountProduct.getDiscount();
      double discountPercent = getDiscountPercent(discount, isFirstPurchaseMember);
      discountedPrice *= (1.0 - discountPercent);
    }

    return Math.round(discountedPrice);
  }

  private boolean isFirstPurchase(final String memberId) {
    return !transactionRepository.existsByCustomerKey(memberId);
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
}
