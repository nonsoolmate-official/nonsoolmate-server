package com.nonsoolmate.member.controller.dto.response;

import com.nonsoolmate.member.entity.enums.MembershipType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MembershipAndTicketResponseDTO", description = "멤버십 및 첨삭권 개수 응답 DTO")
public record MembershipAndTicketResponseDTO(
		@Schema(description = "멤버 이름", example = "류가은") String memberName,
		@Schema(description = "멤버십 유형", example = "BASIC") MembershipType membershipType,
		@Schema(description = "사용자 첨삭권 개수", example = "5") int reviewTicketCount,
		@Schema(description = "사용자 재첨삭권 개수", example = "5") int reReviewticketCount) {
	public static MembershipAndTicketResponseDTO of(
			final String memberName,
			final MembershipType membershipType,
			final int reviewTicketCount,
			final int reReviewticketCount) {

		return new MembershipAndTicketResponseDTO(
				memberName, membershipType, reviewTicketCount, reReviewticketCount);
	}
}
