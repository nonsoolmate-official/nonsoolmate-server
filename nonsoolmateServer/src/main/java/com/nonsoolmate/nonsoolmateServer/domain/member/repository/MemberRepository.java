package com.nonsoolmate.nonsoolmateServer.domain.member.repository;

import static com.nonsoolmate.nonsoolmateServer.domain.member.exception.MemberExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.auth.exception.AuthException;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.PlatformType;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	Optional<Member> findByPlatformTypeAndPlatformId(PlatformType platformType, String platformId);

	Optional<Member> findByMemberId(Long memberId);

	default Member findByMemberIdOrElseThrow(Long memberId) {
		return findByMemberId(memberId).orElseThrow(
			() -> new AuthException(NOT_FOUND_MEMBER));
	}
}
