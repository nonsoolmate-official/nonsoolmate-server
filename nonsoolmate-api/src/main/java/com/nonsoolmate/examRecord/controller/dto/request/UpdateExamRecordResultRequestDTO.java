package com.nonsoolmate.examRecord.controller.dto.request;

import jakarta.validation.constraints.NotNull;

import com.nonsoolmate.examRecord.entity.enums.EditingType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UpdateExamRecordResultRequestDTO", description = "대학 시험 첨삭 결과 수정 요청 DTO")
public record UpdateExamRecordResultRequestDTO(
    @NotNull @Schema(description = "대학 시험 id", example = "1") Long examId,
    @NotNull @Schema(description = "회원 id", example = "xmdhehdjfewl") String memberId,
    @NotNull @Schema(description = "첨삭 유형(EDITING, REVISION)", example = "EDITING")
        EditingType editingType,
    @NotNull @Schema(description = "S3 버킷에 업로드 된 파일명") String examResultFileName) {}
