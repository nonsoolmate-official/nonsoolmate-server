package com.nonsoolmate.coupon.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.nonsoolmate.coupon.controller.dto.request.ApplyCouponRequestDTO;
import com.nonsoolmate.coupon.controller.dto.request.IssueCouponRequestDTO;
import com.nonsoolmate.coupon.controller.dto.request.RegisterCouponRequestDTO;
import com.nonsoolmate.coupon.controller.dto.response.GetCouponsResponseDTO;
import com.nonsoolmate.global.security.AuthMember;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Coupon", description = "쿠폰과 관련된 API")
public interface CouponApi {
  /**
   * @breif: 쿠폰 발행 API
   * @note: 서버 내에서 사용하는 API 입니다.
   */
  ResponseEntity<Void> issueCoupon(@RequestBody @Valid IssueCouponRequestDTO requestDTO);

  @Operation(summary = "쿠폰 목록 조회", description = "사용자가 가지고 있는 쿠폰 목록을 조회합니다.")
  ResponseEntity<GetCouponsResponseDTO> getCoupons(
      @Parameter(hidden = true) @AuthMember String memberId);

  @Operation(summary = "쿠폰 등록", description = "쿠폰을 등록합니다.")
  ResponseEntity<Void> registerCoupon(
      @RequestBody @Valid RegisterCouponRequestDTO requestDTO,
      @Parameter(hidden = true) @AuthMember String memberId);

  @Operation(summary = "쿠폰 사용", description = "쿠폰을 사용합니다.")
  ResponseEntity<Void> applyCoupon(
      @RequestBody @Valid ApplyCouponRequestDTO requestDTO,
      @Parameter(hidden = true) @AuthMember String memberId);
}
