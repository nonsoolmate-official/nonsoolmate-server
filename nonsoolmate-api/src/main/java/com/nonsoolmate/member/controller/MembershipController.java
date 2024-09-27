package com.nonsoolmate.member.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.controller.dto.response.MembershipAndTicketResponseDTO;
import com.nonsoolmate.member.service.MembershipService;

@RestController
@RequestMapping("/membership")
@RequiredArgsConstructor
public class MembershipController implements MembershipApi {

	private final MembershipService membershipService;

	@Override
	@GetMapping("/ticket")
	public ResponseEntity<MembershipAndTicketResponseDTO> getMembershipAndTicket(
			@AuthMember final String memberId) {
		return ResponseEntity.ok().body(membershipService.getMembershipAndTicket(memberId));
	}
}
