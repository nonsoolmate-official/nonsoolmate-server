package com.nonsoolmate.nonsoolmateServer.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.auth.controller.dto.request.MemberRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.auth.service.vo.MemberSignUpVO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.PlatformType;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.Role;
import com.nonsoolmate.nonsoolmateServer.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public abstract class AuthService {
	private final MemberRepository memberRepository;

	private static Member createSocialMember(final String email, final String name, final PlatformType platformType,
		final String birthYear,
		final String gender, final String phoneNumber) {
		return Member.builder().email(email).name(name).platformType(platformType).role(Role.USER)
			.birthYear(birthYear)
			.gender(gender).phoneNumber(phoneNumber).build();
	}

	@Transactional
	public abstract MemberSignUpVO saveMemberOrLogin(final String platformType, final MemberRequestDTO request);

	protected Member getMember(final PlatformType platformType, final String email) {
		return memberRepository.findByPlatformTypeAndEmail(platformType, email)
			.orElse(null);
	}

	protected Member saveUser(final MemberRequestDTO request, final String email, final String name,
		final String birthday, final String gender,
		final String phoneNumber) {
		Member newMember = createSocialMember(email, name, PlatformType.of(request.platformType()), birthday, gender,
			phoneNumber);
		return memberRepository.saveAndFlush(newMember);
	}
}
