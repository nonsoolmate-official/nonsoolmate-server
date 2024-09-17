package com.nonsoolmate.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.auth.controller.dto.request.MemberRequestDTO;
import com.nonsoolmate.auth.controller.dto.response.MemberAuthResponseDTO;
import com.nonsoolmate.auth.controller.dto.response.MemberReissueResponseDTO;
import com.nonsoolmate.auth.controller.enums.AuthType;
import com.nonsoolmate.auth.service.AuthServiceProvider;
import com.nonsoolmate.auth.service.vo.MemberSignUpVO;
import com.nonsoolmate.exception.auth.AuthSuccessType;
import com.nonsoolmate.exception.common.BusinessException;
import com.nonsoolmate.exception.common.CommonErrorType;
import com.nonsoolmate.global.security.service.JwtService;
import com.nonsoolmate.member.entity.enums.PlatformType;
import com.nonsoolmate.response.SuccessResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController implements AuthApi {
	private final AuthServiceProvider authServiceProvider;
	private final JwtService jwtService;

	@Override
	@PostMapping("/social/login")
	public ResponseEntity<SuccessResponse<MemberAuthResponseDTO>> login(
			final String authorizationCode, @RequestBody @Valid final MemberRequestDTO request) {
		PlatformType platformType = PlatformType.of(request.platformType());

		MemberSignUpVO vo =
				authServiceProvider
						.getAuthService(platformType)
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
	public ResponseEntity<SuccessResponse<MemberReissueResponseDTO>> reissue(
			HttpServletRequest request) {
		MemberReissueResponseDTO memberReissueResponseDTO = jwtService.reissueToken(request);
		return ResponseEntity.ok()
				.body(SuccessResponse.of(AuthSuccessType.REISSUE_SUCCESS, memberReissueResponseDTO));
	}

	@GetMapping("/error")
	public String error() {
		throw new BusinessException(CommonErrorType.INTERNAL_SERVER_ERROR);
	}
}
