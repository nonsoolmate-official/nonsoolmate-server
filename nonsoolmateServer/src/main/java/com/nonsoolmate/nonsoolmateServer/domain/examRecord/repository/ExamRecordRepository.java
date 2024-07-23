package com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamException;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
	Optional<ExamRecord> findByExamAndMember(Exam university, Member member);

	default ExamRecord findByExamAndMemberOrElseThrowException(Exam university, Member member) {
		return findByExamAndMember(university, member).orElseThrow(
			() -> new ExamException(NOT_FOUND_UNIVERSITY_EXAM_RECORD));
	}

	// TODO: 데모데이 이후에 삭제 필요.
	void deleteAllByMember(Member member);
}
