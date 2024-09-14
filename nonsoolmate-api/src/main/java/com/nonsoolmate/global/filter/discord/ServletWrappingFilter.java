package com.nonsoolmate.global.filter.discord;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.nonsoolmate.discord.CachedBodyRequestWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServletWrappingFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		CachedBodyRequestWrapper cachedBodyRequestWrapper = new CachedBodyRequestWrapper(request);
		filterChain.doFilter(cachedBodyRequestWrapper, response);
	}
}
