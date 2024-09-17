package com.nonsoolmate.global.security.utils;

import static org.springframework.http.HttpHeaders.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

public abstract class RequestUtils {

	public static final String BEARER_HEADER = "Bearer ";

	@Value("${jwt.access.header}")
	private static String accessHeader = "Authorization";

	public static boolean isContainsAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(accessHeader);
		return authorization != null && authorization.startsWith(BEARER_HEADER);
	}

	// 유효한 Authorization Bearer Token에서 AccessToken 만 뽑아오기
	public static String getAuthorizationAccessToken(HttpServletRequest request) {
		// "Bearer " 문자열 제외하고 뽑아오기
		return request.getHeader(AUTHORIZATION).substring(7);
	}
}
