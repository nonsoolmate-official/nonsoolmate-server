package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.ExamImage;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamImageRepository extends JpaRepository<ExamImage, Long> {
    Page<ExamImage> findAllByExamOrderByExamImageIdAscPageAsc(Exam exam, Pageable pageable);

    List<ExamImage> findAllByExamOrderByExamImageIdAsc(Exam exam);

}
