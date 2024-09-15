package com.nonsoolmate.auth.service;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.nonsoolmate.member.entity.enums.PlatformType;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthServiceProvider {
	private static final ConcurrentHashMap<PlatformType, AuthService> authServiceMap = new ConcurrentHashMap<>();

	private final NaverAuthService naverAuthService;

	@PostConstruct
	void init() {
		authServiceMap.put(PlatformType.NAVER, naverAuthService);
	}

	public AuthService getAuthService(final PlatformType platformType) {
		return authServiceMap.get(platformType);
	}
}