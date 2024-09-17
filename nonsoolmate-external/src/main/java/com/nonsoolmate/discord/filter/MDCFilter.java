package com.nonsoolmate.discord.filter;

import java.io.IOException;
import java.util.Objects;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.nonsoolmate.discord.util.HttpRequestUtil;
import com.nonsoolmate.discord.util.MDCUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MDCFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		HttpServletRequest httpReq = WebUtils.getNativeRequest(request, HttpServletRequest.class);

		MDCUtil.setJsonValue(
				MDCUtil.REQUEST_URI_MDC, HttpRequestUtil.getRequestUri(Objects.requireNonNull(httpReq)));
		MDCUtil.setJsonValue(
				MDCUtil.USER_IP_MDC, HttpRequestUtil.getUserIP(Objects.requireNonNull(httpReq)));
		MDCUtil.setJsonValue(MDCUtil.HEADER_MAP_MDC, HttpRequestUtil.getHeaderMap(httpReq));
		MDCUtil.setJsonValue(MDCUtil.USER_REQUEST_COOKIES, HttpRequestUtil.getUserCookies(httpReq));
		MDCUtil.setJsonValue(MDCUtil.PARAMETER_MAP_MDC, HttpRequestUtil.getParamMap(httpReq));
		MDCUtil.setJsonValue(MDCUtil.BODY_MDC, HttpRequestUtil.getBody(httpReq));

		filterChain.doFilter(request, response);
	}
}
