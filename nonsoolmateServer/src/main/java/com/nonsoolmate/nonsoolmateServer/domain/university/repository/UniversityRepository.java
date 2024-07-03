package com.nonsoolmate.nonsoolmateServer.domain.university.repository;

import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UniversityRepository extends JpaRepository<University, Long> {
    List<University> findAll();

    List<University> findAllByOrderByUniversityNameAscUniversityCollegeAsc();

    List<University> findAllByUniversityIdIn(List<Long> universityIds);

}
