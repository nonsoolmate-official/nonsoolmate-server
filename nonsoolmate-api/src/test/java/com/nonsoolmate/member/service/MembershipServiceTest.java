package com.nonsoolmate.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nonsoolmate.member.controller.dto.response.MembershipAndTicketResponseDTO;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.member.entity.enums.PlatformType;
import com.nonsoolmate.member.entity.enums.Role;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.member.repository.MembershipRepository;

@SpringBootTest
@ActiveProfiles("test")
class MembershipServiceTest {
	@InjectMocks MembershipService membershipService;

	@Mock MemberRepository memberRepository;
	@Mock MembershipRepository membershipRepository;

	private static final String MEMBER_ID = "testMemberId";
	private static final String MEMBER_NAME = "testName";

	@Test
	@DisplayName("사용자가 멤버십 결제를 하지 않은 경우에 멤버십과 첨삭권을 조회하는 경우")
	void getMembershipAndTicketWhenMemberNotPaidMembership() {
		// given
		Member expectMember = getExpectMember();
		MembershipAndTicketResponseDTO expectMembershipAndTicketResponseDTO =
				getExpectMembershipAndTicketResponse(MembershipType.NONE);

		given(memberRepository.findByMemberIdOrThrow(anyString())).willReturn(expectMember);
		given(membershipRepository.findMembershipTypeOrThrowNull(any(Member.class))).willReturn(null);

		// when
		MembershipAndTicketResponseDTO membershipAndTicketResponseDTO =
				membershipService.getMembershipAndTicket(MEMBER_ID);

		// then
		assertThat(membershipAndTicketResponseDTO).isEqualTo(expectMembershipAndTicketResponseDTO);
	}

	private Member getExpectMember() {
		return Member.builder()
				.email("testEmail")
				.name("testName")
				.platformType(PlatformType.NAVER)
				.platformId("testPlatformId")
				.role(Role.USER)
				.birthYear("test")
				.build();
	}

	private MembershipAndTicketResponseDTO getExpectMembershipAndTicketResponse(
			MembershipType membershipType) {
		return MembershipAndTicketResponseDTO.of(MEMBER_NAME, membershipType, 0, 0);
	}
}