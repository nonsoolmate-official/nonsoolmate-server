package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityExamRepository extends JpaRepository<Exam, Long> {
    Optional<Exam> findByUniversityExamId(Long universityId);

    List<Exam> findAllByUniversityOrderByUniversityExamYearDesc(University university);
}
