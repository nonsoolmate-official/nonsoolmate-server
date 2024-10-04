package com.nonsoolmate.member.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.controller.dto.response.MembershipAndTicketResponseDTO;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.member.repository.MembershipRepository;
import com.nonsoolmate.product.entity.Product;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MembershipService {
	private final MemberRepository memberRepository;
	private final MembershipRepository membershipRepository;

	private final String BASIC_MEMBERSHIP_TYPE_NAME = "베이직 플랜";
	private final String PREMIUM_MEMBERSHIP_TYPE_NAME = "프리미엄 플랜";

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

	public boolean checkMembership(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		MembershipType membershipType = getMembershipType(member);
		return membershipType != MembershipType.NONE;
	}

	public Membership createMembership(final String memberId, final Product product) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		MembershipType membershipType = getMembershipTypeByProduct(product);
		Membership membership =
				Membership.builder().member(member).membershipType(membershipType).build();
		return membershipRepository.save(membership);
	}

	private MembershipType getMembershipTypeByProduct(final Product product) {
		switch (product.getProductName()) {
			case BASIC_MEMBERSHIP_TYPE_NAME:
				return MembershipType.BASIC;
			case PREMIUM_MEMBERSHIP_TYPE_NAME:
				return MembershipType.PREMIUM;
			default:
				return MembershipType.NONE;
		}
	}
}
