package com.nonsoolmate.nonsoolmateServer.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.TicketResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;

@Service
@Transactional(readOnly = true)
public class MemberService {

	public NameResponseDTO getNickname(final Member member) {
		return NameResponseDTO.of(member.getName());
	}

	public TicketResponseDTO getTicket(final Member member) {
		return TicketResponseDTO.of(member.getName(), member.getReviewTicketCount());
	}

	public ProfileResponseDTO getProfile(final Member member) {
		return ProfileResponseDTO.of(member.getName(), member.getGender(), member.getBirthYear(),
			member.getEmail(), member.getPhoneNumber());
	}
}
