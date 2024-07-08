package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;

public interface ExamRepository extends JpaRepository<Exam, Long> {
	Optional<Exam> findByExamId(Long universityId);

	List<Exam> findAllByUniversityOrderByExamYearDesc(University university);
}
