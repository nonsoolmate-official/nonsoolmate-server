package com.nonsoolmate.teacher.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.university.entity.University;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeacherUniversity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long teacherUniversityId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacher_id")
  @NotNull
  private Teacher teacher;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "university_id")
  @NotNull
  private University university;
}
