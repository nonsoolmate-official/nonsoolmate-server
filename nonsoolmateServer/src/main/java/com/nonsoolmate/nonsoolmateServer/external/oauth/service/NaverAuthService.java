package com.nonsoolmate.nonsoolmateServer.external.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.nonsoolmate.nonsoolmateServer.domain.auth.controller.dto.request.MemberRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthException;
import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthExceptionType;
import com.nonsoolmate.nonsoolmateServer.domain.auth.service.AuthService;
import com.nonsoolmate.nonsoolmateServer.domain.auth.service.vo.MemberSignUpVO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.PlatformType;
import com.nonsoolmate.nonsoolmateServer.domain.member.repository.MemberRepository;
import com.nonsoolmate.nonsoolmateServer.external.oauth.service.vo.NaverMemberVO;
import com.nonsoolmate.nonsoolmateServer.external.oauth.service.vo.NaverTokenVO;
import com.nonsoolmate.nonsoolmateServer.external.oauth.service.vo.enums.AuthType;

@Service
@Transactional(readOnly = true)
public class NaverAuthService extends AuthService {

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

	@Autowired
	public NaverAuthService(MemberRepository memberRepository) {
		super(memberRepository);
	}

	@Override
	@Transactional
	public MemberSignUpVO saveMemberOrLogin(final String authorizationCode, final MemberRequestDTO request) {
		String accessToken = getAccessToken(authorizationCode, clientId, clientSecret, state).getAccess_token();
		NaverMemberVO naverMemberInfo = getNaverMemberInfo(accessToken);
		Member foundMember = getMember(PlatformType.of(request.platformType()),
			naverMemberInfo.getResponse().getId());

		if (foundMember != null) {
			return MemberSignUpVO.of(foundMember, PlatformType.of(request.platformType()), AuthType.LOGIN);
		}
		PlatformType platformType = PlatformType.of(request.platformType());
		Member savedMember = saveMember(platformType, naverMemberInfo.getResponse().getId(),
			naverMemberInfo.getResponse().getEmail(),
			naverMemberInfo.getResponse().getName(),
			naverMemberInfo.getResponse().getBirthyear(), naverMemberInfo.getResponse().getGender(),
			naverMemberInfo.getResponse().getMobile());

		return MemberSignUpVO.of(savedMember, PlatformType.of(request.platformType()), AuthType.SIGN_UP);

	}

	public NaverMemberVO getNaverMemberInfo(final String accessToken) {
		WebClient webClient = WebClient.builder().build();
		try {
			return webClient.post()
				.uri(userInfoUri)
				.header("Authorization", "Bearer " + accessToken)
				.retrieve()
				.bodyToMono(NaverMemberVO.class)
				.block();
		} catch (Exception e) {
			throw new AuthException(AuthExceptionType.INVALID_MEMBER_PLATFORM_AUTHORIZATION_CODE);
		}
	}

	private NaverTokenVO getAccessToken(String authorizationCode, String clientId, String clientSecret, String state) {
		WebClient webClient = WebClient.builder().build();
		return webClient.post()
			.uri(uriBuilder ->
				uriBuilder
					.scheme("https")  // 스킴을 명시적으로 지정
					.host(tokenUriHost)  // 호스트를 명시적으로 지정
					.path(tokenUriPath)
					.queryParam("grant_type", "authorization_code")
					.queryParam("client_id", clientId)
					.queryParam("client_secret", clientSecret)
					.queryParam("code", authorizationCode)
					.queryParam("state", state)
					.build()
			)
			.retrieve()
			.bodyToMono(NaverTokenVO.class)
			.block();
	}
}