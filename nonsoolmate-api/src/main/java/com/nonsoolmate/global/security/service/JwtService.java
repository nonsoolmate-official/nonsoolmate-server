package com.nonsoolmate.global.security.service;

import static com.nonsoolmate.exception.auth.AuthExceptionType.*;

import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nonsoolmate.auth.controller.dto.response.MemberAuthResponseDTO;
import com.nonsoolmate.auth.controller.dto.response.MemberReissueResponseDTO;
import com.nonsoolmate.auth.service.vo.MemberSignUpVO;
import com.nonsoolmate.exception.auth.AuthException;
import com.nonsoolmate.exception.auth.AuthExceptionType;
import com.nonsoolmate.member.entity.enums.Role;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.redis.repository.RedisTokenRepository;
import com.nonsoolmate.redis.repository.vo.RefreshTokenVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
@Transactional(readOnly = true)
public class JwtService {
  private static final String AUTH_USER = "memberId";
  private static final String BEARER = "Bearer ";
  private static final String EMAIL_CLAIM = "email";
  private static final String MEMBER_ID_CLAIM = "memberId";

  @Value("${jwt.access.expiration}")
  private Long accessTokenExpirationPeriod;

  @Value("${jwt.refresh.expiration}")
  private Long refreshTokenExpirationPeriod;

  @Value("${jwt.access.header}")
  private String accessHeader;

  @Value("${jwt.refresh.header}")
  private String refreshHeader;

  private final MemberRepository memberRepository;
  private final RedisTokenRepository redisTokenRepository;

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtTokenValidator jwtTokenValidator;

  @Transactional
  public MemberAuthResponseDTO issueToken(MemberSignUpVO vo) {
    String accessToken =
        jwtTokenProvider.createAccessToken(vo.email(), vo.memberId(), accessTokenExpirationPeriod);

    if (vo.role().equals(Role.USER)) {
      String refreshToken =
          jwtTokenProvider.createRefreshToken(vo.memberId(), refreshTokenExpirationPeriod);
      updateRefreshToken(vo.memberId(), refreshToken);
      return MemberAuthResponseDTO.of(
          vo.memberId(), vo.authType(), vo.name(), accessToken, refreshToken);
    }

    throw new AuthException(AuthExceptionType.UNAUTHORIZED_MEMBER_LOGIN);
  }

  public MemberReissueResponseDTO reissueToken(HttpServletRequest request) {
    String refreshToken = extractRefreshToken(request);

    try {
      validateToken(refreshToken);
    } catch (MalformedJwtException | SignatureException e) {
      throw new AuthException(INVALID_REFRESH_TOKEN);
    } catch (ExpiredJwtException e) {
      throw new AuthException(UNAUTHORIZED_REFRESH_TOKEN);
    }

    Claims tokenClaims = jwtTokenValidator.getTokenClaims(refreshToken);
    RefreshTokenVO foundRefreshToken =
        redisTokenRepository.findByMemberIdOrElseThrowException(
            String.valueOf(tokenClaims.get(MEMBER_ID_CLAIM)));

    if (!foundRefreshToken.getRefreshToken().equals(refreshToken)) {
      throw new AuthException(INVALID_REFRESH_TOKEN);
    }
    String memberId = tokenClaims.get(MEMBER_ID_CLAIM).toString();
    String email = (String) tokenClaims.get(EMAIL_CLAIM);

    String newAccessToken =
        jwtTokenProvider.createAccessToken(email, memberId, accessTokenExpirationPeriod);
    String newRefreshToken =
        jwtTokenProvider.createRefreshToken(memberId, refreshTokenExpirationPeriod);

    updateRefreshToken(memberId, newRefreshToken);

    return MemberReissueResponseDTO.of(memberId, newAccessToken, newRefreshToken);
  }

  public String extractMemberIdFromAccessToken(final String atk) throws JsonProcessingException {
    Claims tokenClaims = jwtTokenValidator.getTokenClaims(atk);
    return JwtTokenValidator.getMemberIdFromClaim(tokenClaims, AUTH_USER);
  }

  public void validateToken(final String atk)
      throws ExpiredJwtException, MalformedJwtException, SignatureException {
    Claims tokenClaims = jwtTokenValidator.getTokenClaims(atk);
    tokenClaims.getExpiration();
  }

  private String extractRefreshToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(refreshHeader))
        .filter(refreshToken -> refreshToken.startsWith(BEARER))
        .map(refreshToken -> refreshToken.replace(BEARER, ""))
        .orElseThrow(() -> new AuthException(INVALID_REFRESH_TOKEN));
  }

  @Transactional
  public void updateRefreshToken(String memberId, String newRefreshToken) {
    RefreshTokenVO refreshTokenVO = redisTokenRepository.findByMemberId(memberId).orElse(null);

    if (refreshTokenVO != null) {
      refreshTokenVO.updateRefreshToken(newRefreshToken);
      redisTokenRepository.save(refreshTokenVO);
      return;
    }

    redisTokenRepository.save(
        RefreshTokenVO.builder()
            .memberId(String.valueOf(memberId))
            .refreshToken(newRefreshToken)
            .build());
  }
}
