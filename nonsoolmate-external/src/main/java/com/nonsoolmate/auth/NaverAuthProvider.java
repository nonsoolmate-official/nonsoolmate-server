package com.nonsoolmate.auth;

import static com.nonsoolmate.exception.auth.AuthExceptionType.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nonsoolmate.auth.vo.NaverMemberVO;
import com.nonsoolmate.auth.vo.NaverTokenVO;
import com.nonsoolmate.exception.auth.AuthException;

@Service
public class NaverAuthProvider {

  @Value("${spring.security.oauth2.client.naver.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.naver.client-secret}")
  private String clientSecret;

  @Value("${spring.security.oauth2.client.naver.state}")
  private String state;

  @Value("${spring.security.oauth2.client.naver.user-info-uri}")
  private String userInfoUri;

  @Value("${spring.security.oauth2.client.naver.token-uri.host}")
  private String tokenUriHost;

  @Value("${spring.security.oauth2.client.naver.token-uri.path}")
  private String tokenUriPath;

  public NaverMemberVO getNaverMemberInfo(final String accessToken) {
    WebClient webClient = WebClient.builder().build();
    try {
      return webClient
          .post()
          .uri(userInfoUri)
          .header("Authorization", "Bearer " + accessToken)
          .retrieve()
          .bodyToMono(NaverMemberVO.class)
          .block();
    } catch (Exception e) {
      throw new AuthException(INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE);
    }
  }

  public NaverTokenVO getAccessToken(String authorizationCode) {
    WebClient webClient = WebClient.builder().build();
    return webClient
        .post()
        .uri(
            uriBuilder ->
                uriBuilder
                    .scheme("https") // 스킴을 명시적으로 지정
                    .host(tokenUriHost) // 호스트를 명시적으로 지정
                    .path(tokenUriPath)
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("client_id", clientId)
                    .queryParam("client_secret", clientSecret)
                    .queryParam("code", authorizationCode)
                    .queryParam("state", state)
                    .build())
        .retrieve()
        .bodyToMono(NaverTokenVO.class)
        .block();
  }
}
