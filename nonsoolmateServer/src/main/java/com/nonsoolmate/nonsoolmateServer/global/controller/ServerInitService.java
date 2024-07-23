package com.nonsoolmate.nonsoolmateServer.global.controller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository.ExamRecordRepository;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServerInitService {
	private final ExamRecordRepository examRecordRepository;

	@Transactional
	public void init(Member member){
		examRecordRepository.deleteAllByMember(member);
	}
}
