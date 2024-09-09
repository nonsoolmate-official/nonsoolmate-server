package com.nonsoolmate.domain.auth.controller.dto.response;

import com.nonsoolmate.external.oauth.service.vo.enums.AuthType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "MemberAuthResponseDTO", description = "소셜 로그인 응답 DTO")
public record MemberAuthResponseDTO(@NotNull @Schema(description = "사용자 id", example = "bcdc8ebd") String memberId,
									@NotNull @Schema(description = "회원가입/로그인 여부", example = "LOGIN 또는 SIGN_UP") AuthType authType,
									@NotNull @Schema(description = "사용자 이름", example = "류가은") String memberName,
									@NotNull @Schema(description = "액세스 토큰") String accessToken,
									@NotNull @Schema(description = "리프레시 토큰") String refreshToken) {
	public static MemberAuthResponseDTO of(String memberId, AuthType authType, String memberName, String accessToken,
		String refreshToken) {
		return new MemberAuthResponseDTO(memberId, authType, memberName, accessToken, refreshToken);
	}
}
