package com.nonsoolmate.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.university.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
  List<University> findAllByUniversityIdIn(List<Long> universityIds);
}
