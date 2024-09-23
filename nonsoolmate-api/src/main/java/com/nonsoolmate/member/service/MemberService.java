package com.nonsoolmate.member.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.controller.dto.request.ProfileRequestDTO;
import com.nonsoolmate.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.member.controller.dto.response.TicketResponseDTO;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.payment.controller.dto.response.CustomerInfoDTO;

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

		return ProfileResponseDTO.of(
				member.getName(),
				member.getGender(),
				member.getBirthYear(),
				member.getEmail(),
				member.getPhoneNumber());
	}

	public CustomerInfoDTO getCustomerInfo(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		return CustomerInfoDTO.of(member.getMemberId(), member.getName(), member.getEmail());
	}

	@Transactional
	public void editProfile(final ProfileRequestDTO profileRequestDTO, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		member.updateMemberProfile(
				profileRequestDTO.name(),
				profileRequestDTO.gender(),
				profileRequestDTO.birthYear(),
				profileRequestDTO.email(),
				profileRequestDTO.phoneNumber());
	}
}
