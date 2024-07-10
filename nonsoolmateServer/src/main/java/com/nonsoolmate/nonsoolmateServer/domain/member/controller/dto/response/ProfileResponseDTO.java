package com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ProfileResponseDTO", description = "프로필 조회 응답 DTO")
public record ProfileResponseDTO(
	@Schema(description = "멤버 이름", example = "홍길동")
	String name,
	@Schema(description = "성별", example = "M")
	String gender,
	@Schema(description = "태어난 연도", example = "2005")
	String birthday,
	@Schema(description = "이메일", example = "qwer@gmail.com")
	String email,
	@Schema(description = "전화번호", example = "010-1234-5678")
	String phoneNumber
) {
	public static ProfileResponseDTO of(final String name, final String gender, final String birthday,
		final String email, final String phoneNumber) {

		return new ProfileResponseDTO(name, gender, birthday, email, phoneNumber);
	}

}
