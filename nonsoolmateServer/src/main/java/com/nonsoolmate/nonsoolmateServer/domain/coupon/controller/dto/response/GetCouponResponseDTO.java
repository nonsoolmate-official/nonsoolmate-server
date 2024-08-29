package com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response;

import java.time.LocalDateTime;

import com.nonsoolmate.nonsoolmateServer.domain.coupon.entity.enums.CouponType;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "GetCouponResponseDTO", description = "쿠폰 조회 DTO")
@Getter
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

	@Schema(description = "RATE 일 경우 할인율", example = "20")
	private final int discountRate;

	@Schema(description = "AMOUNT 일 경우 할인금액", example = "30000")
	private final int discountAmount;

	@Schema(description = "EDIT_TICKET 일 경우 증정 첨삭권 갯수", example = "10")
	private final int ticketCount;

	@Schema(description = "쿠폰 유효 시작기간", example = "2024-08-22T05:16:44.051Z")
	private final LocalDateTime validStartDate;

	@Schema(description = "쿠폰 유효 종료기간", example = "2024-11-13T05:16:44.051Z")
	private final LocalDateTime validEndDate;

	@Schema(description = "쿠폰 사용여부", example = "true")
	private final Boolean isUsed;

	@QueryProjection
	public GetCouponResponseDTO(Long couponMemberId, String couponName, String couponDescription, String couponImageUrl,
		CouponType couponType,
		int discountRate, int discountAmount, int ticketCount, LocalDateTime validStartDate, LocalDateTime validEndDate,
		Boolean isUsed) {
		this.couponMemberId = couponMemberId;
		this.couponName = couponName;
		this.couponDescription = couponDescription;
		this.couponImageUrl = couponImageUrl;
		this.couponType = couponType;
		this.discountRate = discountRate;
		this.discountAmount = discountAmount;
		this.ticketCount = ticketCount;
		this.validStartDate = validStartDate;
		this.validEndDate = validEndDate;
		this.isUsed = isUsed;
	}
}
