package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.ExamImage;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityExamImageRepository extends JpaRepository<ExamImage, Long> {
    Page<ExamImage> findAllByUniversityExamOrderByPageAsc(Exam exam,
                                                                    Pageable pageable);

    List<ExamImage> findAllByUniversityExamOrderByPageAsc(Exam exam);

}
