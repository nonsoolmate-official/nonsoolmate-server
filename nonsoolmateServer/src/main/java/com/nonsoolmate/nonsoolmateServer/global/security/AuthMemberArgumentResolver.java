package com.nonsoolmate.nonsoolmateServer.global.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAuthMemberAnnotation = parameter.getParameterAnnotation(AuthMember.class) != null;
		boolean isMemberIdString = parameter.getParameterType().equals(String.class);
		return  hasAuthMemberAnnotation && isMemberIdString;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		boolean isNotAuthenticatedMember = authentication == null || !authentication.isAuthenticated();
		if (isNotAuthenticatedMember) {
			return null;
		}
		return String.valueOf(authentication.getPrincipal());
	}
}
