package com.nonsoolmate.examRecord.entity;



import static com.nonsoolmate.examRecord.entity.enums.ExamResultStatus.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.nonsoolmate.examRecord.entity.enums.EditingType;
import com.nonsoolmate.examRecord.entity.enums.ExamResultStatus;

public class ExamRecordGroups {

	/**
	 * @note: key = examId
	 * @note: value = List<ExamRecord>, 첨삭 및 재첨삭 포함
	 * */
	private final Map<Long, List<ExamRecord>> examRecordMap;

	public ExamRecordGroups(List<ExamRecord> examRecords) {

		this.examRecordMap = examRecords.stream()
			.collect(Collectors.groupingBy(examRecord -> examRecord.getExam().getExamId()));
	}

	public ExamResultStatus getExamResultStatus(Long examId) {
		Optional<ExamRecord> examRecord = getRecentExamRecord(examId);

		if (examRecord.isEmpty()) {
			return BEFORE_EXAM;
		}

		return examRecord.get().getExamResultStatus();
	}

	private Optional<ExamRecord> getRecentExamRecord(Long examId) {
		if (!examRecordMap.containsKey(examId)) {
			return Optional.empty();
		}

		List<ExamRecord> examRecords = examRecordMap.get(examId);
		Optional<ExamRecord> revisionExamRecord = examRecords.stream()
			.filter(examRecord -> examRecord.getEditingType().equals(EditingType.REVISION))
			.findFirst();

		if (revisionExamRecord.isPresent()) {
			return revisionExamRecord;
		}

		return examRecords.stream()
			.filter(examRecord -> examRecord.getEditingType().equals(EditingType.EDITING))
			.findFirst();
	}
}
