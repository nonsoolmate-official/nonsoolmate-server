package com.nonsoolmate.payment.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CardResponseDTO", description = "고객이 등록한 카드에 대한 응답 DTO")
public record CardResponseDTO(
		@Schema(description = "사용자가 등록한 카드 id", example = "edslskdkdksl") Long cardId,
		@Schema(description = "등록된 카드의 회사", example = "한국") String cardCompany,
		@Schema(description = "등록된 카드의 카드번호", example = "1111-****-1111") String cardNumber) {
	public static CardResponseDTO of(
			final Long cardId, final String cardCompany, final String cardNumber) {
		return new CardResponseDTO(cardId, cardCompany, cardNumber);
	}
}
