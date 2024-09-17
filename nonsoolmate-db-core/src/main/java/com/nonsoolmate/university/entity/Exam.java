package com.nonsoolmate.university.entity;

import jakarta.persistence.Entity;
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
public class Exam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long examId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "college_id")
	private College college;

	@NotNull private String examName;

	@NotNull private String examFileName;

	@NotNull private String examAnswerFileName;

	@NotNull private int examYear;

	@NotNull private int examTimeLimit;

	public String getExamFullName() {
		return this.getCollege().getUniversity().getUniversityName()
				+ " - "
				+ this.getExamYear()
				+ " "
				+ this.getExamName();
	}

	public String getExamListName() {
		return this.getExamYear() + " " + this.getExamName();
	}

	@Builder
	private Exam(
			College college,
			String examName,
			String examFileName,
			String examAnswerFileName,
			int examYear,
			int examTimeLimit) {
		this.college = college;
		this.examName = examName;
		this.examFileName = examFileName;
		this.examAnswerFileName = examAnswerFileName;
		this.examYear = examYear;
		this.examTimeLimit = examTimeLimit;
	}

	public void setExamIdForTest(Long examId) {
		this.examId = examId;
	}
}
