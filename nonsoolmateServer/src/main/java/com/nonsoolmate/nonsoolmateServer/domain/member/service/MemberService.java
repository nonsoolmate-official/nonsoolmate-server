package com.nonsoolmate.nonsoolmateServer.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.controller.dto.response.TicketResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.repository.MemberRepository;
import com.nonsoolmate.nonsoolmateServer.domain.payment.controller.dto.response.CustomerInfoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	public NameResponseDTO getNickname(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		return NameResponseDTO.of(member.getName());
	}

	public TicketResponseDTO getTicket(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		return TicketResponseDTO.of(member.getName(), member.getReviewTicketCount());
	}

	public ProfileResponseDTO getProfile(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		return ProfileResponseDTO.of(member.getName(), member.getGender(), member.getBirthYear(),
			member.getEmail(), member.getPhoneNumber());
	}

	public CustomerInfoDTO getCustomerInfo(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		return CustomerInfoDTO.of(member.getMemberId(), member.getName(), member.getEmail());
	}
}
