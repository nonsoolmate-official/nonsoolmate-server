package com.nonsoolmate.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipType;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
	@Query("SELECT m.membershipType FROM Membership m WHERE m.member = :member")
	Optional<MembershipType> findMembershipTypeByMember(@Param("member") final Member member);

	default MembershipType findMembershipTypeOrThrowNull(final Member member) {
		return findMembershipTypeByMember(member).orElse(null);
	}
}
