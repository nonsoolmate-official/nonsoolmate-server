package com.nonsoolmate.nonsoolmateServer.domain.coupon.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponType {
	RATE("할인율"),
	AMOUNT("할인금액"),
	EDIT_TICKET("첨삭권")
	;

	private final String decription;

}
