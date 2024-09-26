package com.nonsoolmate.payment.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateCardRequestDTO", description = "카드 등록 요청 DTO")
public record CreateCardRequestDTO(
		@Parameter(description = "Toss에서 받은 customerKey", required = true) @NotNull String customerKey,
		@Parameter(description = "Toss에서 받은 authKey", required = true) @NotNull String authKey) {}
