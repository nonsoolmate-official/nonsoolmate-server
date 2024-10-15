package com.nonsoolmate.product.entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.discountProduct.entity.DiscountProduct;
import com.nonsoolmate.product.entity.enums.ProductType;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @NotNull private String productName;

  @Enumerated(EnumType.STRING)
  @NotNull
  private ProductType productType;

  @Min(0)
  private int reviewTicketCount;

  @Min(0)
  private int reReviewTicketCount;

  @Min(0)
  private long price;

  @NotNull private String description;

  @NotNull private LocalDateTime startDate;

  @NotNull private LocalDateTime endDate;

  public List<String> getDescriptions() {
    return Arrays.asList(description.split(","));
  }

  public long getDiscountAmount(
      List<DiscountProduct> discountProduct, Optional<Coupon> usedCoupon) {
    long productPrice = this.price;
    long discountAmount = 0;

    for (DiscountProduct discount : discountProduct) {
      long curDiscountAmount = (long) (productPrice * discount.getDiscount().getDiscountRate());
      discountAmount += curDiscountAmount;
      productPrice -= curDiscountAmount;
    }

    if (usedCoupon.isPresent()) {
      discountAmount += (long) (productPrice * usedCoupon.get().getDiscountRate());
    }
    return discountAmount;
  }
}
