package com.nonsoolmate.global.security.service;

import static org.springframework.http.HttpHeaders.*;

import java.security.Key;

import jakarta.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
public class JwtTokenValidator {

	public static final String BEARER_HEADER = "Bearer ";

	@Value("${jwt.access.header}")
	private static String accessHeader = "Authorization";

	private final Key key;

	public JwtTokenValidator(@Value("${jwt.secretKey}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public static boolean isContainsAccessToken(HttpServletRequest request) {
		String authorization = request.getHeader(accessHeader);
		return authorization != null && authorization.startsWith(BEARER_HEADER);
	}

	// 유효한 Authorization Bearer Token에서 AccessToken 만 뽑아오기
	public static String getAuthorizationAccessToken(HttpServletRequest request) {
		// "Bearer " 문자열 제외하고 뽑아오기
		return request.getHeader(AUTHORIZATION).substring(7);
	}

	public static String getMemberIdFromClaim(Claims claims, String infoName) {
		return claims.get(infoName, String.class);
	}

	public Claims getTokenClaims(final String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}
}
