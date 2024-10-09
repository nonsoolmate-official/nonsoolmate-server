package com.nonsoolmate.discountProduct.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.discount.entity.Discount;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.discountProduct.repository.DiscountProductRepository;
import com.nonsoolmate.payment.service.TransactionService;
import com.nonsoolmate.product.entity.Product;
import com.nonsoolmate.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiscountProductService {
  private final DiscountProductRepository discountProductRepository;
  private final ProductRepository productRepository;
  private final TransactionService transactionService;

  public long getDiscountedProductPrice(final Long productId, final String memberId) {
    Product product = productRepository.findByProductIdOrThrow(productId);
    double discountedPrice = product.getPrice();
    List<DiscountProduct> defaultDiscountProducts =
        getDiscountProductsByProductAndMemberId(product, memberId);

    for (DiscountProduct discountProduct : defaultDiscountProducts) {
      Discount discount = discountProduct.getDiscount();
      discountedPrice *= (1.0 - discount.getDiscountRate());
    }

    return Math.round(discountedPrice);
  }

  public List<DiscountProduct> getDiscountProductsByProductAndMemberId(
      final Product product, final String memberId) {
    boolean isFirstPurchaseMember = transactionService.isFirstPurchase(memberId);
    if (isFirstPurchaseMember) {
      return getDefaultDiscountProductsByProduct(product);
    }
    return discountProductRepository.findAllByProductIdAndDiscountIsContinuous(product);
  }

  public List<DiscountProduct> getDefaultDiscountProductsByProduct(final Product product) {
    return discountProductRepository.findAllByProduct(product);
  }
}
