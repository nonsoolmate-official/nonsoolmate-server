package com.nonsoolmate.tag.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nonsoolmate.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	List<Tag> findAllByTeacherId(Long teacherId);
}
