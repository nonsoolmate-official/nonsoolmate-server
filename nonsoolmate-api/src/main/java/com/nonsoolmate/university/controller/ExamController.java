package com.nonsoolmate.university.controller;



import static com.nonsoolmate.exception.university.ExamSuccessType.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.university.controller.dto.response.ExamAndAnswerResponseDTO;
import com.nonsoolmate.university.controller.dto.response.ExamInfoResponseDTO;
import com.nonsoolmate.university.controller.dto.response.ExamUrlResponseDTO;

import com.nonsoolmate.university.service.ExamService;
import com.nonsoolmate.exception.university.ExamSuccessType;
import com.nonsoolmate.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/college/exam")
@RequiredArgsConstructor
public class ExamController implements ExamApi {
	private final ExamService examService;

	@Override
	@GetMapping("/{id}/info")
	public ResponseEntity<SuccessResponse<ExamInfoResponseDTO>> getExamInfo(
		@PathVariable("id") final Long examId) {
		return ResponseEntity.ok().body(SuccessResponse.of(ExamSuccessType.GET_EXAM_SUCCESS,
			examService.getExamInfo(examId)));
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<SuccessResponse<ExamUrlResponseDTO>> getExamFile(
		@PathVariable("id") final Long id) {
		return ResponseEntity.ok().body(SuccessResponse.of(ExamSuccessType.GET_EXAM_FILE_SUCCESS,
			examService.getExamFile(id)));
	}

	@Override
	@GetMapping("{id}/answer")
	public ResponseEntity<SuccessResponse<ExamAndAnswerResponseDTO>> getExamAndAnswer(
		@PathVariable("id") Long examId
	) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_EXAM_IMAGE_AND_ANSWER_SUCCESS,
			examService.getExamAndAnswer(examId)));
	}
}
