package com.nonsoolmate.nonsoolmateServer.global.security.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthException;
import com.nonsoolmate.nonsoolmateServer.global.response.ErrorResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (AuthException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding("UTF-8");
			String error = objectMapper.writeValueAsString(ErrorResponse.of(e.getExceptionType()));
			response.getWriter().write(error);
		}
	}
}
