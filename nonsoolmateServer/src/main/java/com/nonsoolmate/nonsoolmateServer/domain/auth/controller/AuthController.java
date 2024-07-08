package com.nonsoolmate.nonsoolmateServer.domain.auth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.nonsoolmateServer.domain.auth.controller.dto.request.MemberRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.auth.controller.dto.response.MemberAuthResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.auth.controller.dto.response.MemberReissueResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthSuccessType;
import com.nonsoolmate.nonsoolmateServer.domain.auth.service.AuthServiceProvider;
import com.nonsoolmate.nonsoolmateServer.domain.auth.service.vo.MemberSignUpVO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.PlatformType;
import com.nonsoolmate.nonsoolmateServer.external.oauth.service.vo.enums.AuthType;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.BusinessException;
import com.nonsoolmate.nonsoolmateServer.global.error.exception.CommonErrorType;
import com.nonsoolmate.nonsoolmateServer.global.jwt.service.JwtService;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController implements AuthApi {
	private final AuthServiceProvider authServiceProvider;
	private final JwtService jwtService;

	@Value("${spring.security.oauth2.client.naver.client-id}")
	private String clientId;
	@Value("${spring.security.oauth2.client.naver.redirect-uri}")
	private String redirectUri;

	@Override
	@PostMapping("/social/login")
	public ResponseEntity<SuccessResponse<MemberAuthResponseDTO>> login(
		final String authorizationCode,
		@RequestBody @Valid final
		MemberRequestDTO request) {
		PlatformType platformType = PlatformType.of(request.platformType());

		MemberSignUpVO vo = authServiceProvider.getAuthService(platformType)
			.saveMemberOrLogin(authorizationCode, request);
		MemberAuthResponseDTO responseDTO = jwtService.issueToken(vo);
		if (responseDTO.authType().equals(AuthType.SIGN_UP)) {
			return ResponseEntity.status(HttpStatus.CREATED)
				.body(SuccessResponse.of(AuthSuccessType.SIGN_UP_SUCCESS, responseDTO));
		}
		return ResponseEntity.ok().body(SuccessResponse.of(AuthSuccessType.LOGIN_SUCCESS, responseDTO));
	}

	@Override
	@PostMapping("/reissue")
	public ResponseEntity<SuccessResponse<MemberReissueResponseDTO>> reissue(HttpServletRequest request) {
		MemberReissueResponseDTO memberReissueResponseDTO = jwtService.reissueToken(request);
		return ResponseEntity.ok().body(SuccessResponse.of(AuthSuccessType.REISSUE_SUCCESS, memberReissueResponseDTO));
	}

	@GetMapping("/authTest")
	public String authTest(HttpServletRequest request, HttpServletResponse response) {
		String redirectURL = "https://nid.naver.com/oauth2.0/authorize?client_id=" + clientId
			+ "&redirect_uri=" + redirectUri + "&response_type=code";
		try {
			response.sendRedirect(
				redirectURL);
		} catch (Exception e) {
			log.info("authTest = {}", e);
		}

		return "SUCCESS";
	}

	@GetMapping("/error")
	public String error() {
		throw new BusinessException(CommonErrorType.INTERNAL_SERVER_ERROR);
	}
}
