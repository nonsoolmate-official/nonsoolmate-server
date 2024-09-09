package com.nonsoolmate.domain.selectCollege.controller;

import static com.nonsoolmate.domain.selectCollege.exception.SelectCollegeSuccessType.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.domain.selectCollege.controller.dto.request.SelectUniversityRequestDTO;
import com.nonsoolmate.domain.selectCollege.controller.dto.response.SelectCollegeExamsResponseDTO;
import com.nonsoolmate.domain.selectCollege.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.domain.selectCollege.controller.dto.response.SelectCollegeUpdateResponseDTO;
import com.nonsoolmate.domain.selectCollege.service.SelectCollegeService;
import com.nonsoolmate.global.response.SuccessResponse;
import com.nonsoolmate.global.security.AuthMember;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/select-college")
public class SelectCollegeController implements SelectCollegeApi {

	private final SelectCollegeService selectCollegeService;

	@Override
	@GetMapping
	public ResponseEntity<SuccessResponse<List<SelectCollegeResponseDTO>>> getSelectColleges(
		@AuthMember String memberId) {

		return ResponseEntity.ok().body(SuccessResponse.of(GET_SELECT_COLLEGES_SUCCESS,
			selectCollegeService.getSelectColleges(memberId)));
	}

	@Override
	@GetMapping("/exam")
	public ResponseEntity<SuccessResponse<List<SelectCollegeExamsResponseDTO>>> getSelectCollegeExams(
		@AuthMember final String memberId) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_SELECT_COLLEGE_EXAMS_SUCCESS,
			selectCollegeService.getSelectCollegeExams(memberId)));
	}

	@Override
	@PatchMapping
	public ResponseEntity<SuccessResponse<SelectCollegeUpdateResponseDTO>> patchSelectColleges(
		@AuthMember String memberId,
		@RequestBody @Valid final List<SelectUniversityRequestDTO> request) {

		List<Long> selectedUniversityIds = request.stream()
			.map(SelectUniversityRequestDTO::universityId)
			.toList();

		return ResponseEntity.ok().body(SuccessResponse.of(PATCH_SELECT_COLLEGES_SUCCESS,
			selectCollegeService.patchSelectColleges(memberId, selectedUniversityIds)));
	}
}
