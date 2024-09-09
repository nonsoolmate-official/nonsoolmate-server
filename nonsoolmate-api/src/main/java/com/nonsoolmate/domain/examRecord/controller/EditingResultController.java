package com.nonsoolmate.domain.examRecord.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.domain.examRecord.controller.dto.response.EditingResultDTO;
import com.nonsoolmate.domain.examRecord.entity.enums.EditingType;
import com.nonsoolmate.domain.examRecord.service.ExamRecordService;
import com.nonsoolmate.global.security.AuthMember;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/college/exam")
@RequiredArgsConstructor
public class EditingResultController implements EditingResultApi {
	private final ExamRecordService examRecordService;

	@GetMapping("/{exam-id}/exam-record/result")
	public ResponseEntity<EditingResultDTO> getExamRecordResult(@PathVariable("exam-id") final long examId,
		@RequestParam("type") final EditingType type, @AuthMember final String memberId) {
		return ResponseEntity.ok().body(examRecordService.getExamRecordEditingResult(examId, type, memberId));
	}
}
