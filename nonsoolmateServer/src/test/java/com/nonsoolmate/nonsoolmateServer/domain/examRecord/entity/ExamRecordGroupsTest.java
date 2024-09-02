package com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.EditingType;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.PlatformType;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.Role;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.College;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;

class ExamRecordGroupsTest {

	@Test
	@DisplayName("시험 기록이 없는 경우 '시험응시전' 상태가 된다.")
	void hasNoExamRecord() {
		// given
		Exam examFixture = createExamFixture();
		Member member = new Member("email1", "홍길동", PlatformType.NAVER, "pId1", Role.USER, "2010", "M",
			"010-1234-5678");

		ExamRecord examRecord1 = createExamRecord(examFixture, member, ExamResultStatus.REVIEW_FINISH,
			EditingType.EDITING, 120, "examRecordSheetFileName1");
		ExamRecord examRecord2 = createExamRecord(examFixture, member, ExamResultStatus.RE_REVIEW_ONGOING,
			EditingType.EDITING, 75, "examRecordSheetFileName2");

		List<ExamRecord> examRecordFixture = List.of(examRecord1, examRecord2);
		ExamRecordGroups examRecordGroups = new ExamRecordGroups(examRecordFixture);

		// when
		ExamResultStatus examResultStatus = examRecordGroups.getExamResultStatus(0L);

		// then
		Assertions.assertThat(examResultStatus).isEqualTo(ExamResultStatus.BEFORE_EXAM);
	}

	@Test
	@DisplayName("재첨삭를 진행할 경우 '재첨삭 진행 중' 상태가 된다.")
	void progressRevisionEdit() {
		// given
		Exam examFixture = createExamFixture();
		Member member = new Member("email1", "홍길동", PlatformType.NAVER, "pId1", Role.USER, "2010", "M",
			"010-1234-5678");

		ExamRecord examRecord1 = createExamRecord(examFixture, member, ExamResultStatus.REVIEW_FINISH,
			EditingType.EDITING, 120, "examRecordSheetFileName1");
		ExamRecord examRecord2 = createExamRecord(examFixture, member, ExamResultStatus.RE_REVIEW_ONGOING,
			EditingType.REVISION, 75, "examRecordSheetFileName2");

		List<ExamRecord> examRecordFixture = List.of(examRecord1, examRecord2);
		ExamRecordGroups examRecordGroups = new ExamRecordGroups(examRecordFixture);

		// when
		ExamResultStatus examResultStatus = examRecordGroups.getExamResultStatus(1L);

		// then
		Assertions.assertThat(examResultStatus).isEqualTo(ExamResultStatus.RE_REVIEW_ONGOING);
	}

	@Test
	@DisplayName("첨삭 결과를 받은 경우 '첨삭 완료' 상태가 된다.")
	void progressEditingEdit() {
		// given
		Exam examFixture = createExamFixture();
		Member member = new Member("email1", "홍길동", PlatformType.NAVER, "pId1", Role.USER, "2010", "M",
			"010-1234-5678");

		ExamRecord examRecord1 = createExamRecord(examFixture, member, ExamResultStatus.REVIEW_FINISH,
			EditingType.EDITING, 120, "examRecordSheetFileName1");

		List<ExamRecord> examRecordFixture = List.of(examRecord1);
		ExamRecordGroups examRecordGroups = new ExamRecordGroups(examRecordFixture);

		// when
		ExamResultStatus examResultStatus = examRecordGroups.getExamResultStatus(1L);

		// then
		Assertions.assertThat(examResultStatus).isEqualTo(ExamResultStatus.REVIEW_FINISH);
	}

	private ExamRecord createExamRecord(Exam exam, Member member, ExamResultStatus examResultStatus,
		EditingType editingType, int timeTakeExam, String examRecordSheetFileName) {
		return ExamRecord.builder()
			.exam(exam)
			.member(member)
			.examResultStatus(examResultStatus)
			.editingType(editingType)
			.timeTakeExam(timeTakeExam)
			.examRecordSheetFileName(examRecordSheetFileName)
			.build();
	}

	private Exam createExamFixture() {
		University university = new University("고려대학교");

		College college = College.builder()
			.university(university)
			.collegeName("인문")
			.build();

		Exam exam = Exam.builder()
			.college(college)
			.examName("인문1")
			.examFileName("ExamFile1")
			.examAnswerFileName("AnswerFile1")
			.examYear(2022)
			.examTimeLimit(120)
			.build();
		exam.setExamIdForTest(1L);

		return exam;
	}
}
