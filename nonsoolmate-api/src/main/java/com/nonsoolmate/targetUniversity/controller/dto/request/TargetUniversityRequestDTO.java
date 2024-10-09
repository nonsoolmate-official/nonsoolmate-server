package com.nonsoolmate.targetUniversity.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public record TargetUniversityRequestDTO(
		@NotNull @Schema(description = "사용자가 선택한 대학 id", example = "1") Long universityId) {}
