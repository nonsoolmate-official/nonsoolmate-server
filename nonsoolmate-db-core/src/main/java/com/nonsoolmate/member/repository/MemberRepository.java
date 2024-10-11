package com.nonsoolmate.member.repository;

import static com.nonsoolmate.exception.member.MemberExceptionType.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.exception.auth.AuthException;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.PlatformType;

public interface MemberRepository extends JpaRepository<Member, String> {
  Optional<Member> findByEmail(String email);

  Optional<Member> findByPlatformTypeAndPlatformId(PlatformType platformType, String platformId);

  Optional<Member> findByMemberId(String memberId);

  default Member findByMemberIdOrThrow(String memberId) {
    return findByMemberId(memberId).orElseThrow(() -> new AuthException(NOT_FOUND_MEMBER));
  }
}
