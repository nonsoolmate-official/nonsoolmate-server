package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.UniversityExamImage;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityExamImageRepository extends JpaRepository<UniversityExamImage, Long> {
    Page<UniversityExamImage> findAllByUniversityExamOrderByPageAsc(Exam exam,
                                                                    Pageable pageable);

    List<UniversityExamImage> findAllByUniversityExamOrderByPageAsc(Exam exam);

}
