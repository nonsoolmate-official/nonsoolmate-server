package com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception;

import com.nonsoolmate.nonsoolmateServer.global.error.exception.SuccessType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExamRecordSuccessType implements SuccessType {
    GET_EXAM_RECORD_SUCCESS(HttpStatus.OK, "첨삭 PDF, 해제 PDF 조회에 성공했습니다."),
    GET_EXAM_RECORD_RESULT_SUCCESS(HttpStatus.OK, "첨삭 PDF 조회에 성공했습니다."),
    GET_EXAM_RECORD_SHEET_PRESIGNED_SUCCESS(HttpStatus.OK, "답안지 업로드 PreSignedUrl 발급에 성공했습니다."),
    CREATE_EXAM_RECORD_SUCCESS(HttpStatus.CREATED, "대학 시험 기록에 성공했습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus status() {
        return this.status;
    }

    @Override
    public String message() {
        return this.message;
    }
}
