package com.nonsoolmate.nonsoolmateServer.domain.selectCollege.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.selectCollege.exception.SelectCollegeSuccessType.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.selectCollege.controller.dto.request.SelectUniversityRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectCollege.controller.dto.response.SelectCollegeExamsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectCollege.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectCollege.controller.dto.response.SelectCollegeUpdateResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectCollege.service.SelectCollegeService;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/select-university")
public class SelectCollegeController implements SelectCollegeApi {

	private final SelectCollegeService selectCollegeService;

	@Override
	@GetMapping
	public ResponseEntity<SuccessResponse<List<SelectCollegeResponseDTO>>> getSelectColleges(
		@AuthUser Member member) {

		return ResponseEntity.ok().body(SuccessResponse.of(GET_SELECT_COLLEGES_SUCCESS,
			selectCollegeService.getSelectColleges(member)));
	}

	@Override
	@GetMapping("/exam")
	public ResponseEntity<SuccessResponse<List<SelectCollegeExamsResponseDTO>>> getSelectCollegeExams(
		@AuthUser final Member member) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_SELECT_COLLEGE_EXAMS_SUCCESS,
			selectCollegeService.getSelectCollegeExams(member)));
	}

	@Override
	@PatchMapping
	public ResponseEntity<SuccessResponse<SelectCollegeUpdateResponseDTO>> patchSelectUniversities(
		@AuthUser Member member,
		@RequestBody @Valid final List<SelectUniversityRequestDTO> request) {

		List<Long> selectedUniversityIds = request.stream()
			.map(SelectUniversityRequestDTO::universityId)
			.toList();

		return ResponseEntity.ok().body(SuccessResponse.of(PATCH_SELECT_COLLEGES_SUCCESS,
			selectCollegeService.patchSelectColleges(member, selectedUniversityIds)));
	}
}
