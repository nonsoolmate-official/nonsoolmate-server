package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
	List<University> findAll();

	List<University> findAllByOrderByUniversityNameAscUniversityCollegeAsc();

	List<University> findAllByUniversityIdIn(List<Long> universityIds);

}
