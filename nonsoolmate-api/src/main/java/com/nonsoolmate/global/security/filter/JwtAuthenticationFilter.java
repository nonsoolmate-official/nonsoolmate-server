package com.nonsoolmate.global.security.filter;

import static com.nonsoolmate.domain.auth.exception.AuthExceptionType.*;
import static com.nonsoolmate.global.security.config.SecurityConfig.*;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nonsoolmate.domain.auth.exception.AuthException;
import com.nonsoolmate.global.jwt.service.JwtService;
import com.nonsoolmate.global.jwt.utils.RequestUtils;
import com.nonsoolmate.global.security.MemberAuthentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		if (Arrays.stream(AUTH_WHITELIST)
			.anyMatch(whiteUrl -> request.getRequestURI().equals(whiteUrl))) {
			filterChain.doFilter(request, response);
			return;
		}

		if (Arrays.stream(AUTH_WHITELIST_WILDCARD)
			.anyMatch(
				whiteUrl -> request.getRequestURI().startsWith(whiteUrl.substring(0, whiteUrl.length() - 3)))) {
			filterChain.doFilter(request, response);
			return;
		}

		if (!RequestUtils.isContainsAccessToken(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		String authorizationAccessToken = RequestUtils.getAuthorizationAccessToken(request);

		try {
			jwtService.validateToken(authorizationAccessToken);

			String memberId = jwtService.extractMemberIdFromAccessToken(authorizationAccessToken);
			MemberAuthentication memberAuthentication = new MemberAuthentication(memberId, null, null);

			log.info("Authentication Principal : {}", memberAuthentication.getPrincipal());

			SecurityContextHolder.getContext().setAuthentication(memberAuthentication);

		} catch (JsonProcessingException | MalformedJwtException | SignatureException e) {
			throw new AuthException(INVALID_ACCESS_TOKEN);
		} catch (ExpiredJwtException e) {
			throw new AuthException(UNAUTHORIZED_ACCESS_TOKEN);
		} catch (AuthException e) {
			throw new AuthException(e.getExceptionType());
		}

		filterChain.doFilter(request, response);
	}

}