package com.nonsoolmate.selectCollege.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.selectCollege.entity.SelectCollege;

public interface SelectCollegeRepository extends JpaRepository<SelectCollege, Long> {

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("DELETE FROM SelectCollege s WHERE s.member.memberId = :memberId")
  void deleteAllByMemberId(String memberId);

  @Query(
      "select sc from SelectCollege sc "
          + "join fetch sc.college c "
          + "join fetch c.university u "
          + "where sc.member.memberId =:memberId "
          + "order by u.universityName asc, c.collegeName asc")
  List<SelectCollege> findAllByMemberOrderByUniversityNameAscCollegeNameAsc(
      @Param("memberId") String memberId);

  List<SelectCollege> findAllByMember(Member member);
}
