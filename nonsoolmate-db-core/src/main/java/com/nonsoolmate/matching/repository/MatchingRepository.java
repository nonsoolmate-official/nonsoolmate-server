package com.nonsoolmate.matching.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nonsoolmate.matching.entity.Matching;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

	@Query(
			"SELECT m "
					+ "FROM Matching m "
					+ "JOIN FETCH m.member "
					+ "JOIN FETCH m.teacher "
					+ "WHERE m.member.memberId = :memberId")
	Optional<Matching> findByMemberIdWithTeacherAndMember(@Param("memberId") String memberId);
}
