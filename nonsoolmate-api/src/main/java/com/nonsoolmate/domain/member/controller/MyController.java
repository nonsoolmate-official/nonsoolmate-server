package com.nonsoolmate.domain.member.controller;

import static com.nonsoolmate.domain.member.exception.MemberSuccessType.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.domain.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.domain.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.domain.member.controller.dto.response.TicketResponseDTO;
import com.nonsoolmate.domain.member.exception.MemberSuccessType;
import com.nonsoolmate.domain.member.service.MemberService;
import com.nonsoolmate.global.response.SuccessResponse;
import com.nonsoolmate.global.security.AuthMember;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController implements MemberApi {
	private final MemberService memberService;

	@Override
	@GetMapping("/name")
	public ResponseEntity<SuccessResponse<NameResponseDTO>> getName(@AuthMember final String memberId) {
		return ResponseEntity.ok()
			.body(SuccessResponse.of(MemberSuccessType.GET_MEMBER_NAME_SUCCESS,
				memberService.getNickname(memberId)));
	}

	@Override
	@GetMapping("/ticket")
	public ResponseEntity<SuccessResponse<TicketResponseDTO>> getTicket(@AuthMember final String memberId) {
		return ResponseEntity.ok()
			.body(SuccessResponse.of(MemberSuccessType.GET_MEMBER_TICKET_SUCCESS,
				memberService.getTicket(memberId)));
	}

	@Override
	@GetMapping("/profile")
	public ResponseEntity<SuccessResponse<ProfileResponseDTO>> getProfile(@AuthMember final String memberId) {
		ProfileResponseDTO responseDTO = memberService.getProfile(memberId);

		return ResponseEntity.ok().body(SuccessResponse.of(GET_MEMBER_PROFILE_SUCCESS, responseDTO));
	}
}