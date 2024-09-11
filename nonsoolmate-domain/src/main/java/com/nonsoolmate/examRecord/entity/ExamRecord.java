package com.nonsoolmate.examRecord.entity;

import com.nonsoolmate.examRecord.entity.enums.EditingType;
import com.nonsoolmate.examRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.university.entity.Exam;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	uniqueConstraints = {
		@UniqueConstraint(name = "UK_EXAM_MEMBER_EDITING_TYPE", columnNames = {"exam_id", "member_id", "editing_type"})
	}
)
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

	@Enumerated(EnumType.STRING)
	@NotNull
	private EditingType editingType;

	private int timeTakeExam;

	@NotNull
	private String examRecordSheetFileName;

	private String examRecordResultFileName;

	@Builder
	public ExamRecord(final Exam exam, final Member member, final ExamResultStatus examResultStatus,
		final EditingType editingType,
		final int timeTakeExam, final String examRecordSheetFileName) {
		this.exam = exam;
		this.member = member;
		this.examResultStatus = examResultStatus;
		this.editingType = editingType;
		this.timeTakeExam = timeTakeExam;
		this.examRecordSheetFileName = examRecordSheetFileName;
	}
}