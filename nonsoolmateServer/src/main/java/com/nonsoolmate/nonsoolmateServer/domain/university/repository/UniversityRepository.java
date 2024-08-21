package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;

public interface UniversityRepository extends JpaRepository<University, Long> {
}
