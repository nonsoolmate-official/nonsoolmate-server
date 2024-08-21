package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.exception.SelectUniversitySuccessType.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.request.SelectUniversityRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityExamsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityUpdateResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.service.SelectUniversityService;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/select-university")
public class SelectUniversityController implements SelectUniversityApi {

	private final SelectUniversityService selectUniversityService;

	@Override
	@GetMapping
	public ResponseEntity<SuccessResponse<List<SelectCollegeResponseDTO>>> getSelectColleges(
		@AuthUser Member member) {

		return ResponseEntity.ok().body(SuccessResponse.of(GET_SELECT_UNIVERSITIES_SUCCESS,
			selectUniversityService.getSelectColleges(member)));
	}

	@Override
	@GetMapping("/exam")
	public ResponseEntity<SuccessResponse<List<SelectUniversityExamsResponseDTO>>> getSelectUniversityExams(
		@AuthUser final Member member) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_SELECT_UNIVERSITY_EXAMS_SUCCESS,
			selectUniversityService.getSelectUniversityExams(member)));
	}

	@Override
	@PatchMapping
	public ResponseEntity<SuccessResponse<SelectUniversityUpdateResponseDTO>> patchSelectUniversities(
		@AuthUser Member member,
		@RequestBody @Valid final List<SelectUniversityRequestDTO> request) {

		List<Long> selectedUniversityIds = request.stream()
			.map(SelectUniversityRequestDTO::universityId)
			.toList();

		return ResponseEntity.ok().body(SuccessResponse.of(PATCH_SELECT_UNIVERSITIES_SUCCESS,
			selectUniversityService.patchSelectUniversities(member, selectedUniversityIds)));
	}
}
