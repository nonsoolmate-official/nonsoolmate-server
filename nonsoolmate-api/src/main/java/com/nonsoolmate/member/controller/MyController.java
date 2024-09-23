package com.nonsoolmate.member.controller;

import static com.nonsoolmate.exception.member.MemberSuccessType.*;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nonsoolmate.exception.member.MemberSuccessType;
import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.controller.dto.request.ProfileRequestDTO;
import com.nonsoolmate.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.member.controller.dto.response.TicketResponseDTO;
import com.nonsoolmate.member.service.MemberService;
import com.nonsoolmate.response.SuccessResponse;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController implements MemberApi {
	private final MemberService memberService;

	@Override
	@GetMapping("/name")
	public ResponseEntity<SuccessResponse<NameResponseDTO>> getName(
			@AuthMember final String memberId) {
		return ResponseEntity.ok()
				.body(
						SuccessResponse.of(
								MemberSuccessType.GET_MEMBER_NAME_SUCCESS, memberService.getNickname(memberId)));
	}

	@Override
	@GetMapping("/ticket")
	public ResponseEntity<SuccessResponse<TicketResponseDTO>> getTicket(
			@AuthMember final String memberId) {
		return ResponseEntity.ok()
				.body(
						SuccessResponse.of(
								MemberSuccessType.GET_MEMBER_TICKET_SUCCESS, memberService.getTicket(memberId)));
	}

	@Override
	@GetMapping("/profile")
	public ResponseEntity<SuccessResponse<ProfileResponseDTO>> getProfile(
			@AuthMember final String memberId) {
		ProfileResponseDTO responseDTO = memberService.getProfile(memberId);

		return ResponseEntity.ok().body(SuccessResponse.of(GET_MEMBER_PROFILE_SUCCESS, responseDTO));
	}

	@Override
	@PutMapping("/profile")
	public ResponseEntity<Void> editProfile(
			@RequestBody @Valid ProfileRequestDTO profileRequestDTO, @AuthMember String memberId) {
		memberService.editProfile(profileRequestDTO, memberId);

		return ResponseEntity.ok().build();
	}
}
