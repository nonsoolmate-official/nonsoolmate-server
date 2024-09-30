package com.nonsoolmate.teacher.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.teacher.entity.Teacher;
import com.nonsoolmate.teacher.entity.TeacherUniversity;

public interface TeacherUniversityRepository extends JpaRepository<TeacherUniversity, Long> {

	List<TeacherUniversity> findAllByTeacher(Teacher teacher);
}
