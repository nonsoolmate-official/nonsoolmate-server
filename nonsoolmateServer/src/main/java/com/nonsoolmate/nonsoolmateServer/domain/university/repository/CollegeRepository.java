package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.College;

public interface CollegeRepository extends JpaRepository<CollegeRepository, Long> {
	@Query("select c from College c order by c.university.universityName asc, c.collegeName asc")
	List<College> findAllByOrderByUniversityNameAscCollegeNameAsc();
}
