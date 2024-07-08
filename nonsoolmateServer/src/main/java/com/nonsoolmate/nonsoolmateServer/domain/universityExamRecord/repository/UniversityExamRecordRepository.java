package com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.exception.UniversityExamRecordExceptionType.NOT_FOUND_UNIVERSITY_EXAM_RECORD;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamException;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.entity.UniversityExamRecord;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityExamRecordRepository extends JpaRepository<UniversityExamRecord, Long> {
    Optional<UniversityExamRecord> findByUniversityExamAndMember(Exam university, Member member);

    default UniversityExamRecord findByUniversityExamAndMemberOrElseThrowException(Exam university,
                                                                                   Member member) {
        return findByUniversityExamAndMember(university, member).orElseThrow(
                () -> new UniversityExamException(NOT_FOUND_UNIVERSITY_EXAM_RECORD));
    }
}
