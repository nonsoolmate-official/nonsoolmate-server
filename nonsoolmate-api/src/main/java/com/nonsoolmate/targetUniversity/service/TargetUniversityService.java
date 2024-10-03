package com.nonsoolmate.targetUniversity.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.targetUniversity.controller.dto.TargetUniversityResponseDto;
import com.nonsoolmate.targetUniversity.repository.TargetUniversityRepository;
import com.nonsoolmate.university.entity.University;
import com.nonsoolmate.university.repository.UniversityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TargetUniversityService {
	private final UniversityRepository universityRepository;
	private final TargetUniversityRepository targetUniversityRepository;

	public List<TargetUniversityResponseDto> getTargetUniversities() {
		List<University> universities = universityRepository.findAll();

		return universities.stream().map(TargetUniversityResponseDto::of).toList();
	}
}
