package com.nonsoolmate.nonsoolmateServer.domain.member.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.member.exception.MemberSuccessType.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.TicketResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.exception.MemberSuccessType;
import com.nonsoolmate.nonsoolmateServer.domain.member.service.MemberService;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthMember;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController implements MemberApi {
	private final MemberService memberService;

	@Override
	@GetMapping("/name")
	public ResponseEntity<SuccessResponse<NameResponseDTO>> getName(@AuthMember final Member member) {
		return ResponseEntity.ok()
			.body(SuccessResponse.of(MemberSuccessType.GET_MEMBER_NAME_SUCCESS,
				memberService.getNickname(member)));
	}

	@Override
	@GetMapping("/ticket")
	public ResponseEntity<SuccessResponse<TicketResponseDTO>> getTicket(@AuthMember final Member member) {
		return ResponseEntity.ok()
			.body(SuccessResponse.of(MemberSuccessType.GET_MEMBER_TICKET_SUCCESS,
				memberService.getTicket(member)));
	}

	@Override
	@GetMapping("/profile")
	public ResponseEntity<SuccessResponse<ProfileResponseDTO>> getProfile(@AuthMember final Member member) {
		ProfileResponseDTO responseDTO = memberService.getProfile(member);

		return ResponseEntity.ok().body(SuccessResponse.of(GET_MEMBER_PROFILE_SUCCESS, responseDTO));
	}
}
