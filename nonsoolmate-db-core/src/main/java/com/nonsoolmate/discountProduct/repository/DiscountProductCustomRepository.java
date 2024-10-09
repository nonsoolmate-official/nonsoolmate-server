package com.nonsoolmate.discountProduct.repository;

import java.util.List;

import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.product.entity.Product;

public interface DiscountProductCustomRepository {
  List<DiscountProduct> findAllByProductIdAndDiscountIsContinuous(Product product);
}
