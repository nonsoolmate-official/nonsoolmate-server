package com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.EditingType;
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

}
