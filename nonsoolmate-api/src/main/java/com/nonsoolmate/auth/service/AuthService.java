package com.nonsoolmate.auth.service;

import org.springframework.dao.DataIntegrityViolationException;

import com.nonsoolmate.auth.controller.dto.request.MemberRequestDTO;
import com.nonsoolmate.auth.service.vo.MemberSignUpVO;
import com.nonsoolmate.exception.auth.AuthException;
import com.nonsoolmate.exception.auth.AuthExceptionType;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.PlatformType;
import com.nonsoolmate.member.entity.enums.Role;
import com.nonsoolmate.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AuthService {
	private final MemberRepository memberRepository;

	private static Member createSocialMember(final String email, final String name, final PlatformType platformType,
		final String platformId, final String birthYear,
		final String gender, final String phoneNumber) {
		return Member.builder().email(email).name(name).platformType(platformType).role(Role.USER)
			.platformId(platformId)
			.birthYear(birthYear)
			.gender(gender).phoneNumber(phoneNumber).build();
	}

	public abstract MemberSignUpVO saveMemberOrLogin(final String platformType, final MemberRequestDTO request);

	protected Member getMember(final PlatformType platformType, final String email) {
		return memberRepository.findByPlatformTypeAndPlatformId(platformType, email)
			.orElse(null);
	}

	protected Member saveMember(final PlatformType platformType, final String platformId, final String email,
		final String name, final String birthday, final String gender, final String phoneNumber) {
		Member newMember = createSocialMember(email, name, platformType, platformId, birthday, gender,
			phoneNumber);
		try {
			return memberRepository.save(newMember);
		} catch (DataIntegrityViolationException e) {
			throw new AuthException(AuthExceptionType.INVALID_REQUEST_LOGIN);
		}
	}
}
