package com.nonsoolmate.member.repository;

import static com.nonsoolmate.exception.member.MembershipExceptionType.NOT_FOUND_MEMBERSHIP_TYPE;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nonsoolmate.exception.member.MemberException;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.Membership;
import com.nonsoolmate.member.entity.enums.MembershipStatus;
import com.nonsoolmate.member.entity.enums.MembershipType;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
	@Query("SELECT m.membershipType FROM Membership m WHERE m.member = :member")
	Optional<MembershipType> findMembershipTypeByMember(@Param("member") final Member member);

	default MembershipType findMembershipTypeOrThrowNull(final Member member) {
		return findMembershipTypeByMember(member).orElse(null);
	}

	Optional<Membership> findByMember(final Member member);

	default Membership findByMemberOrThrow(Member member) {
		return findByMember(member).orElseThrow(() -> new MemberException(NOT_FOUND_MEMBERSHIP_TYPE));
	}

	List<Membership> findAllByStatusAndMemberIn(
			MembershipStatus membershipStatus, List<Member> members);
}
