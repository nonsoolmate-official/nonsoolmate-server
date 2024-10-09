package com.nonsoolmate.targetUniversity.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.targetUniversity.controller.dto.response.TargetUniversityResponseDTO;
import com.nonsoolmate.targetUniversity.service.TargetUniversityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/target-university")
public class TargetUniversityController implements TargetUniversityApi {
	private final TargetUniversityService targetUniversityService;

	@Override
	@GetMapping
	public ResponseEntity<List<TargetUniversityResponseDTO>> getTargetUniversities(
			@AuthMember String memberId) {

		return ResponseEntity.ok().body(targetUniversityService.getTargetUniversities());
	}
}
