package com.nonsoolmate.member.entity.enums;


import static com.nonsoolmate.exception.auth.AuthExceptionType.*;

import java.util.Arrays;

import com.nonsoolmate.exception.auth.AuthException;

public enum PlatformType {
	NAVER, NONE;

	public static PlatformType of(String platformType) {
		return Arrays.stream(PlatformType.values()).filter(type -> type.toString().equals(platformType)).findAny()
			.orElseThrow(() -> new AuthException(INVALID_PLATFORM_TYPE));
	}
}
