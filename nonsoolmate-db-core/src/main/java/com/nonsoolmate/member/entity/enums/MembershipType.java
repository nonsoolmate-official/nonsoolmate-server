package com.nonsoolmate.member.entity.enums;

import static com.nonsoolmate.exception.member.MemberExceptionType.*;
import static com.nonsoolmate.exception.member.MembershipExceptionType.*;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

import com.nonsoolmate.exception.common.BusinessException;

@RequiredArgsConstructor
public enum MembershipType {
	NONE("멤버십 없음"),
	BASIC("베이직 플랜"),
	PREMIUM("프리미엄 플랜");

	private final String description;

	public static MembershipType getMembershipType(String description) {
		return Arrays.stream(MembershipType.values())
				.filter(type -> type.description.equals(description))
				.findFirst()
				.orElseThrow(() -> new BusinessException(NOT_FOUND_MEMBERSHIP_TYPE));
	}
}
