package com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
	List<ExamRecord> findByExamAndMember(Exam university, Member member);
}
