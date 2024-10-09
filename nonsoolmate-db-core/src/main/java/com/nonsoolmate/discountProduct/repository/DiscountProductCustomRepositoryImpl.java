package com.nonsoolmate.discountProduct.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.nonsoolmate.discount.entity.QDiscount;
import com.nonsoolmate.discount.entity.enums.DiscountType;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.discountProduct.entity.QDiscountProduct;
import com.nonsoolmate.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@RequiredArgsConstructor
public class DiscountProductCustomRepositoryImpl implements DiscountProductCustomRepository {
  private final JPAQueryFactory queryFactory;

  @Override
  public List<DiscountProduct> findAllByProductIdAndDiscountIsContinuous(final Product product) {
    QDiscountProduct discountProduct = QDiscountProduct.discountProduct;
    QDiscount discount = QDiscount.discount;

    return queryFactory
        .selectFrom(discountProduct)
        .join(discountProduct.discount, discount)
        .where(
            discountProduct
                .product
                .eq(product)
                .and(discount.discountType.eq(DiscountType.CONTINUOUS)))
        .fetch();
  }
}
