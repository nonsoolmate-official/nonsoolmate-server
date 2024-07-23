package com.nonsoolmate.nonsoolmateServer.global.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository.ExamRecordRepository;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/init")
public class ServerInitController {

	private final ExamRecordRepository examRecordRepository;

	@GetMapping
	public ResponseEntity<Void> init(@AuthUser Member member){

		examRecordRepository.deleteAllByMember(member);

		return ResponseEntity.ok().build();
	}
}
