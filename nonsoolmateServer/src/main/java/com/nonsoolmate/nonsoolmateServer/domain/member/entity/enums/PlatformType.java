package com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums;

import static com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthExceptionType.*;

import java.util.Arrays;

import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthException;

public enum PlatformType {
	NAVER, NONE;

	public static PlatformType of(String platformType) {
		return Arrays.stream(PlatformType.values()).filter(type -> type.toString().equals(platformType)).findAny()
			.orElseThrow(() -> new AuthException(INVALID_PLATFORM_TYPE));
	}
}
