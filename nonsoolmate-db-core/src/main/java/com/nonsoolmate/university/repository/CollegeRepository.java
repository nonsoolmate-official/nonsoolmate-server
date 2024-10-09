package com.nonsoolmate.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nonsoolmate.university.entity.College;

public interface CollegeRepository extends JpaRepository<College, Long> {
  @Query("select c from College c order by c.university.universityName asc, c.collegeName asc")
  List<College> findAllOrderByUniversityNameAscCollegeNameAsc();

  List<College> findAllByCollegeIdIn(List<Long> collegeIds);
}
