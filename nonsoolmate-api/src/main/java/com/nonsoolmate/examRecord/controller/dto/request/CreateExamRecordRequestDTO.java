package com.nonsoolmate.examRecord.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import com.nonsoolmate.examRecord.entity.enums.EditingType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CreateExamRecordRequestDTO", description = "대학 시험 기록 생성 요청 DTO")
public record CreateExamRecordRequestDTO(
    @NotNull @Schema(description = "대학 시험 id", example = "1") Long examId,
    @Positive @Schema(description = "사용자가 해당 시험을 보는데 걸린 시간(초 단위)") int memberTakeTimeExam,
    @NotNull @Schema(description = "PreSingedUrl 발급 시 전달 받은 파일 이름") String memberSheetFileName,
    @NotNull @Schema(description = "첨삭 유형(EDITING, REVISION)", example = "REVISION")
        EditingType editingType) {
  public static CreateExamRecordRequestDTO of(
      Long examId, int memberTakeTimeExam, String memberSheetFileName, EditingType editingType) {
    return new CreateExamRecordRequestDTO(
        examId, memberTakeTimeExam, memberSheetFileName, editingType);
  }
}
