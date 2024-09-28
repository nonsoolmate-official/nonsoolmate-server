package com.nonsoolmate.selectCollege.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public record SelectCollegeRequestDTO(
		@NotNull @Schema(description = "사용자가 선택한 단과 대학 id", example = "1") Long collegeId) {}
