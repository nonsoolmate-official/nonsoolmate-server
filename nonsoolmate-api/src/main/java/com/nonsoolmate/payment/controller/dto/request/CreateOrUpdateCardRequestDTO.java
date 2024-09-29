package com.nonsoolmate.payment.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateOrUpdateCardRequestDTO", description = "카드 등록 또는 변경 요청 DTO")
public record CreateOrUpdateCardRequestDTO(
		@Parameter(description = "Toss에서 받은 authKey", required = true) @NotNull String authKey) {}
