package com.nonsoolmate.member.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.controller.dto.response.MembershipAndTicketResponseDTO;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.member.repository.MembershipRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipService {
	private final MemberRepository memberRepository;
	private final MembershipRepository membershipRepository;

	public MembershipAndTicketResponseDTO getMembershipAndTicket(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		MembershipType membershipType = getMembershipType(member);
		return MembershipAndTicketResponseDTO.of(
				member.getName(),
				membershipType,
				member.getReviewTicketCount(),
				member.getReReviewTicketCount());
	}

	private MembershipType getMembershipType(final Member member) {
		MembershipType membershipType = membershipRepository.findMembershipTypeOrThrowNull(member);
		boolean existMembership = membershipType != null;
		return existMembership ? membershipType : MembershipType.NONE;
	}
}
