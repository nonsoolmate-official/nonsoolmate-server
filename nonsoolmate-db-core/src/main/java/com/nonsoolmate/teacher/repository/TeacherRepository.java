package com.nonsoolmate.teacher.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.teacher.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {}
