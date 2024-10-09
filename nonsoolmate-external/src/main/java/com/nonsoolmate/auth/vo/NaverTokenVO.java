package com.nonsoolmate.auth.vo;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverTokenVO {
  private String access_token;
  private String refresh_token;
  private String token_type;
  private String expires_in;
}
