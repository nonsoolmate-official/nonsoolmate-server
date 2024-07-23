package com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExamRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long examRecordId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exam_id")
	private Exam exam;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ExamResultStatus examResultStatus;

	private int timeTakeExam;

	@NotNull
	private String examRecordSheetFileName;  // 내 답안

	private String examRecordResultFileName;  // 첨삭

	@Builder
	public ExamRecord(final Exam exam, final Member member, final ExamResultStatus examResultStatus,
		final int timeTakeExam, final String examRecordSheetFileName) {
		this.exam = exam;
		this.member = member;
		this.examResultStatus = examResultStatus;
		this.timeTakeExam = timeTakeExam;
		this.examRecordSheetFileName = examRecordSheetFileName;
	}

	public void updateRecordResultFileName(String examRecordResultFileName){
		this.examRecordResultFileName = examRecordResultFileName;
		this.examResultStatus = ExamResultStatus.FINISH;
	}
}