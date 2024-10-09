package com.nonsoolmate.targetUniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.targetUniversity.entity.TargetUniversity;

public interface TargetUniversityRepository extends JpaRepository<TargetUniversity, Long> {
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("DELETE FROM TargetUniversity tu WHERE tu.member = :member")
	void deleteAllByMember(@Param("member") Member member);
}
