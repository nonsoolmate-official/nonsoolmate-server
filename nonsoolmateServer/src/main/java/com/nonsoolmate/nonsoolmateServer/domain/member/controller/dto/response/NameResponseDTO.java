package com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "NameResponseDTO", description = "멤버 이름 응답 DTO")
public record NameResponseDTO(@Schema(description = "멤버 이름", example = "류가은") String memberName) {
	public static NameResponseDTO of(final String memberName) {
		return new NameResponseDTO(memberName);
	}
}
