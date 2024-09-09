package com.nonsoolmate.domain.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.domain.university.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
