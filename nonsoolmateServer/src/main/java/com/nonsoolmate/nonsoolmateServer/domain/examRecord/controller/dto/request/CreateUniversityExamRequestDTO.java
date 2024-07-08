package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "CreateUniversityExamRequestDTO", description = "대학 시험 기록 생성 요청 DTO")
public record CreateUniversityExamRequestDTO(@NotNull @Schema(description = "대학 시험 id", example="1") Long universityExamId,
                                             @Positive @Schema(description = "사용자가 해당 시험을 보는데 걸린 시간(초 단위)") int memberTakeTimeExam,
                                             @NotNull @Schema(description="PreSingedUrl 발급 시 전달 받은 파일 이름") String memberSheetFileName) {
    public static CreateUniversityExamRequestDTO of(Long universityExamId, int memberTakeTimeExam,
                                                    String memberSheetFileName) {
        return new CreateUniversityExamRequestDTO(universityExamId, memberTakeTimeExam, memberSheetFileName);
    }
}
