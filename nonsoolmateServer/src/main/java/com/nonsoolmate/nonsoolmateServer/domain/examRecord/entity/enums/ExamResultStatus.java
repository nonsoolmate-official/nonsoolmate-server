package com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ExamResultStatus {
	REVIEW_ONGOING("첨삭 진행 중"),
	REVIEW_FINISH("첨삭 완료"),
	RE_REVIEW_ONGOING("재첨삭 진행 중"),
	RE_REVIEW_FINISH("재첨삭 완료");

	private final String status;
}
