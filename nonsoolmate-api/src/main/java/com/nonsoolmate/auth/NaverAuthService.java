package com.nonsoolmate.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.auth.controller.dto.request.MemberRequestDTO;
import com.nonsoolmate.auth.service.AuthService;
import com.nonsoolmate.auth.service.vo.MemberSignUpVO;
import com.nonsoolmate.auth.vo.NaverMemberVO;
import com.nonsoolmate.auth.vo.enums.AuthType;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.PlatformType;
import com.nonsoolmate.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class NaverAuthService extends AuthService {
	private final NaverAuthProvider naverAuthProvider;

	@Autowired
	public NaverAuthService(MemberRepository memberRepository, NaverAuthProvider naverAuthProvider) {
		super(memberRepository);
		this.naverAuthProvider = naverAuthProvider;
	}

	@Override
	@Transactional
	public MemberSignUpVO saveMemberOrLogin(final String authorizationCode, final MemberRequestDTO request) {
		String accessToken = naverAuthProvider.getAccessToken(authorizationCode).getAccess_token();
		NaverMemberVO naverMemberInfo = naverAuthProvider.getNaverMemberInfo(accessToken);
		Member foundMember = getMember(PlatformType.of(request.platformType()),
			naverMemberInfo.getResponse().getId());

		if (foundMember != null) {
			return MemberSignUpVO.of(foundMember, PlatformType.of(request.platformType()), AuthType.LOGIN);
		}
		PlatformType platformType = PlatformType.of(request.platformType());
		Member savedMember = saveMember(platformType, naverMemberInfo.getResponse().getId(),
			naverMemberInfo.getResponse().getEmail(),
			naverMemberInfo.getResponse().getName(),
			naverMemberInfo.getResponse().getBirthyear(), naverMemberInfo.getResponse().getGender(),
			naverMemberInfo.getResponse().getMobile());

		return MemberSignUpVO.of(savedMember, PlatformType.of(request.platformType()), AuthType.SIGN_UP);
	}
}