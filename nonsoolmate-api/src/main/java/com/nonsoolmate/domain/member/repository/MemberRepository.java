package com.nonsoolmate.domain.member.repository;

import static com.nonsoolmate.domain.member.exception.MemberExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.domain.auth.exception.AuthException;
import com.nonsoolmate.domain.member.entity.Member;
import com.nonsoolmate.domain.member.entity.enums.PlatformType;

public interface MemberRepository extends JpaRepository<Member, String> {
	Optional<Member> findByEmail(String email);

	Optional<Member> findByPlatformTypeAndPlatformId(PlatformType platformType, String platformId);

	Optional<Member> findByMemberId(String memberId);

	default Member findByMemberIdOrThrow(String memberId) {
		return findByMemberId(memberId).orElseThrow(
			() -> new AuthException(NOT_FOUND_MEMBER));
	}
}
