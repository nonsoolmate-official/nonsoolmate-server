package com.nonsoolmate.domain.member.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TicketResponseDTO", description = "멤버 첨삭권 응답 DTO")
public record TicketResponseDTO(@Schema(description = "멤버 이름", example = "류가은") String memberName,
								@Schema(description = "사용자 첨삭권 개수", example = "5") int ticketCount) {
	public static TicketResponseDTO of(final String memberName, final int ticketCount) {
		return new TicketResponseDTO(memberName, ticketCount);
	}
}
