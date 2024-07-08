package com.nonsoolmate.nonsoolmateServer.global.jwt.utils;

import static com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthSuccessType.*;
import static org.springframework.http.HttpHeaders.*;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthExceptionType;
import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthSuccessType;
import com.nonsoolmate.nonsoolmateServer.global.response.ErrorResponse;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class RequestUtils {

	@Value("${jwt.access.header}")
	private static String accessHeader = "Authorization";
	public static final String BEARER_HEADER = "Bearer ";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static boolean isContainsAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(accessHeader);
		return authorization != null
			&& authorization.startsWith(BEARER_HEADER);
	}

	// 유효한 Authorization Bearer Token에서 AccessToken 만 뽑아오기
	public static String getAuthorizationAccessToken(HttpServletRequest request) {
		// "Bearer " 문자열 제외하고 뽑아오기
		return request.getHeader(AUTHORIZATION).substring(7);
	}

	public static void setBodyOnResponse(HttpServletResponse response, AuthSuccessType success, Object bodyData) {
		response.setStatus(LOGIN_SUCCESS.getHttpStatusCode());
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		try {
			String body = objectMapper.writeValueAsString(SuccessResponse.of(success, bodyData));
			response.getWriter().write(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setErrorBodyOnResponse(HttpServletResponse response, AuthExceptionType error) {

		response.setStatus(INVALID_ACCESS_TOKEN.getHttpStatusCode());
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		try {
			String body = objectMapper.writeValueAsString(ErrorResponse.of(error));
			response.getWriter().write(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

