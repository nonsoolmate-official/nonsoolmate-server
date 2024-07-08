package com.nonsoolmate.nonsoolmateServer.external.oauth.service.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverTokenVO {
	private String access_token;
	private String refresh_token;
	private String token_type;
	private String expires_in;
}
