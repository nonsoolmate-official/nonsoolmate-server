package com.nonsoolmate.nonsoolmateServer.domain.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UniversityExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityExamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

    @NotNull
    private String universityExamName;

    @NotNull
    private String universityExamFileName;

    @NotNull
    private String universityExamAnswerFileName;

    @NotNull
    private int universityExamYear;

    @NotNull
    private int universityExamTimeLimit;

    public String getUniversityExamFullName(){
        return this.getUniversity().getUniversityName() + " - " + this.getUniversityExamYear() + " " + this.getUniversityExamName();
    }

    public String getUniversityExamListName(){
        return this.getUniversityExamYear() + " " + this.getUniversityExamName();
    }
}