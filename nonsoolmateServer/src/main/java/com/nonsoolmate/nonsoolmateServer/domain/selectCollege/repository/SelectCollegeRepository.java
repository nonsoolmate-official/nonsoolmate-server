package com.nonsoolmate.nonsoolmateServer.domain.selectCollege.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.selectCollege.entity.SelectCollege;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;

public interface SelectCollegeRepository extends JpaRepository<SelectCollege, Long> {

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("DELETE FROM SelectCollege s WHERE s.member.memberId = :memberId")
	void deleteAllByMemberId(String memberId);

	@Query("select sc from SelectCollege sc where sc.member =:member order by sc.college.university.universityName asc, sc.college.collegeName asc")
	List<SelectCollege> findAllByMemberOrderByUniversityNameAscCollegeNameAsc(@Param("member") Member member);

	Optional<SelectCollege> findByMemberAndCollege(Member member, University university);

	Set<Long> findUniversityIdsByMember(Member member);
}
