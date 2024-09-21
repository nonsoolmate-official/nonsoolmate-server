package com.nonsoolmate.global.security.service;

import java.security.Key;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Slf4j
@Component
public class JwtTokenProvider {
	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String EMAIL_CLAIM = "email";
	private static final String MEMBER_ID_CLAIM = "memberId";

	private final Key key;

	public JwtTokenProvider(@Value("${jwt.secretKey}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String createAccessToken(String email, String memberId, Long expirationTime) {
		Date now = new Date();

		return Jwts.builder()
				.setSubject(ACCESS_TOKEN_SUBJECT)
				.claim(MEMBER_ID_CLAIM, memberId)
				.claim(EMAIL_CLAIM, email)
				.setIssuedAt(now) // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + expirationTime)) // 토큰 만료 시간 설정
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}

	public String createRefreshToken(String memberId, Long expirationTime) {
		Date now = new Date();

		return Jwts.builder()
				.setSubject(REFRESH_TOKEN_SUBJECT)
				.claim(MEMBER_ID_CLAIM, memberId)
				.setIssuedAt(now) // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + expirationTime)) // 토큰 만료 시간 설정
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
}
