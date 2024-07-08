package com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordExceptionType.NOT_FOUND_UNIVERSITY_EXAM_RECORD;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamException;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityExamRecordRepository extends JpaRepository<ExamRecord, Long> {
    Optional<ExamRecord> findByUniversityExamAndMember(Exam university, Member member);

    default ExamRecord findByUniversityExamAndMemberOrElseThrowException(Exam university,
                                                                                   Member member) {
        return findByUniversityExamAndMember(university, member).orElseThrow(
                () -> new ExamException(NOT_FOUND_UNIVERSITY_EXAM_RECORD));
    }
}
