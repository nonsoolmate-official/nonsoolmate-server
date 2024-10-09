package com.nonsoolmate.university.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nonsoolmate.university.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
  Optional<Exam> findByExamId(Long examId);

  @Query(
      "SELECT e "
          + "FROM Exam e "
          + "WHERE e.college.collegeId IN :collegeIds "
          + "ORDER BY e.examYear DESC ")
  List<Exam> findAllByCollegeIdInOrderByExamYearDesc(List<Long> collegeIds);
}
