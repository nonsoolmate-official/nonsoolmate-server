package com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordExceptionType.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.EditingType;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordException;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
	List<ExamRecord> findByExamAndMember(Exam university, Member member);

	Optional<ExamRecord> findByExamAndMemberAndEditingType(Exam exam, Member member, EditingType editingType);

	@Query("SELECT r "
		+ "FROM ExamRecord r "
		+ "WHERE r.exam.examId IN :examIds "
		+ "AND r.member.memberId = :memberId")
	List<ExamRecord> findAllByExamIdInAndMemberId(List<Long> examIds, String memberId);

	@Query("SELECT r "
		+ "FROM ExamRecord r "
		+ "WHERE r.exam.examId = :examId "
		+ "AND r.member.memberId = :memberId "
		+ "AND r.editingType = :editingType")
	Optional<ExamRecord> findByExamAndEditingType(
		@Param("examId") Long examId,
		@Param("editingType") EditingType editingType,
		@Param("memberId") String memberId);

	default ExamRecord findByExamAndMemberAndEditingTypeOrThrow(Long examId, EditingType editingType, String memberId) {
		return findByExamAndEditingType(examId, editingType, memberId)
			.orElseThrow(() -> new ExamRecordException(NOT_FOUND_EXAM_RECORD));
	}
}
