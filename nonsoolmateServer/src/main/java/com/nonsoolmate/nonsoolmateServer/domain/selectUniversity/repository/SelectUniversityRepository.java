package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.repository;


import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.entity.SelectUniversity;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SelectUniversityRepository extends JpaRepository<SelectUniversity, Long> {
    void deleteAllByMember(Member member);

    @Query("select su from SelectUniversity su where su.member =:member order by su.university.universityName asc, su.university.universityCollege asc")
    List<SelectUniversity> findAllByMemberOrderByUniversityNameASCUniversityCollegeAsc(Member member);

    Optional<SelectUniversity> findByMemberAndUniversity(Member member, University university);
}
