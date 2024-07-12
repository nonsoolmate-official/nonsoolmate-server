package com.nonsoolmate.nonsoolmateServer.global.jwt.service.vo;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@RedisHash(value = "refresh", timeToLive = 604800016)
public class RefreshTokenVO {
	@Id
	@Indexed
	private String memberId;

	private String refreshToken;

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Builder
	public RefreshTokenVO(String memberId, String refreshToken) {
		this.memberId = memberId;
		this.refreshToken = refreshToken;
	}
}
