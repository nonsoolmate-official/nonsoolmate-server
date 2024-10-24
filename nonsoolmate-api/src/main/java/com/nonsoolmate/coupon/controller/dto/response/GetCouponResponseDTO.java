package com.nonsoolmate.coupon.controller.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.nonsoolmate.coupon.entity.enums.CouponType;
import com.nonsoolmate.couponMember.repository.dto.CouponResponseDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "GetCouponResponseDTO", description = "쿠폰 조회 DTO")
@Getter
@RequiredArgsConstructor
public class GetCouponResponseDTO {
  @Schema(description = "사용자가 등록한 coupon의 id", example = "1")
  private final Long couponMemberId;

  @Schema(description = "쿠폰 이름", example = "쿠폰 이름입니다.")
  private final String couponName;

  @Schema(description = "쿠폰 상세 설명", example = "쿠폰 설명입니다.")
  private final String couponDescription;

  @Schema(description = "쿠폰 이미지 url", example = "[url] 형식")
  private final String couponImageUrl;

  @Schema(description = "쿠폰 타입, </br> RATE, AMOUNT, EDIT_TICKET 중에 하나입니다.", example = "RATE")
  private final CouponType couponType;

  @Schema(description = "RATE 일 경우 할인율", example = "0.2")
  private final double discountRate;

  @Schema(description = "AMOUNT 일 경우 할인금액", example = "30000")
  private final int discountAmount;

  @Schema(description = "EDIT_TICKET 일 경우 증정 첨삭권 갯수", example = "10")
  private final int ticketCount;

  @Schema(description = "쿠폰 유효 시작기간", example = "2024-08-22T05:16:44.051Z")
  private final LocalDateTime validStartDate;

  @Schema(description = "쿠폰 유효 종료기간", example = "2024-11-13T05:16:44.051Z")
  private final LocalDateTime validEndDate;

  public static GetCouponResponseDTO of(CouponResponseDTO dto) {
    return new GetCouponResponseDTO(
        dto.getCouponMemberId(),
        dto.getCouponName(),
        dto.getCouponDescription(),
        dto.getCouponImageUrl(),
        dto.getCouponType(),
        dto.getDiscountRate(),
        dto.getDiscountAmount(),
        dto.getTicketCount(),
        dto.getValidStartDate(),
        dto.getValidEndDate());
  }
}
