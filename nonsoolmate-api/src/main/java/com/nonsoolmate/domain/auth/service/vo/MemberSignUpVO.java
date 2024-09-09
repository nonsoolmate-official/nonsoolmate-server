package com.nonsoolmate.domain.auth.service.vo;

import com.nonsoolmate.domain.member.entity.Member;
import com.nonsoolmate.domain.member.entity.enums.PlatformType;
import com.nonsoolmate.domain.member.entity.enums.Role;
import com.nonsoolmate.external.oauth.service.vo.enums.AuthType;

import lombok.Builder;

@Builder
public record MemberSignUpVO(String memberId, String email, String name, PlatformType platformType, Role role,
							 String birthYear, String gender, String phoneNumber, AuthType authType) {
	public static MemberSignUpVO of(Member member, PlatformType platformType, AuthType authtype) {
		return MemberSignUpVO.builder()
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.name(member.getName())
			.platformType(platformType)
			.role(Role.USER)
			.birthYear(member.getBirthYear())
			.gender(member.getGender())
			.phoneNumber(member.getPhoneNumber())
			.authType(authtype)
			.build();
	}
}
