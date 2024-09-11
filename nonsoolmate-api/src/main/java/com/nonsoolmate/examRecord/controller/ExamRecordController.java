package com.nonsoolmate.examRecord.controller;


import static com.nonsoolmate.exception.examRecord.ExamRecordSuccessType.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.aws.service.vo.PreSignedUrlVO;
import com.nonsoolmate.examRecord.controller.dto.request.CreateExamRecordRequestDTO;
import com.nonsoolmate.examRecord.controller.dto.response.ExamRecordIdResponse;
import com.nonsoolmate.examRecord.controller.dto.response.ExamRecordResponseDTO;
import com.nonsoolmate.examRecord.controller.dto.response.ExamRecordResultResponseDTO;
import com.nonsoolmate.examRecord.controller.dto.response.ExamSheetPreSignedUrlResponseDTO;
import com.nonsoolmate.examRecord.service.ExamRecordService;
import com.nonsoolmate.examRecord.service.ExamRecordSheetService;
import com.nonsoolmate.examRecord.entity.enums.EditingType;
import com.nonsoolmate.exception.examRecord.ExamRecordSuccessType;
import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/college/exam-record")
@RequiredArgsConstructor
public class ExamRecordController implements ExamRecordApi {

	private final ExamRecordService examRecordService;
	private final ExamRecordSheetService examRecordSheetService;

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<SuccessResponse<ExamRecordResponseDTO>> getExamRecord(
		@PathVariable("id") Long examId, @AuthMember String memberId) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_EXAM_RECORD_SUCCESS,
			examRecordService.getExamRecord(examId, memberId)));
	}

	@Override
	@GetMapping("/result/{id}")
	public ResponseEntity<SuccessResponse<ExamRecordResultResponseDTO>> getExamRecordResult(
		@PathVariable("id") Long examId, @AuthMember String memberId) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_EXAM_RECORD_RESULT_SUCCESS,
			examRecordService.getExamRecordResult(examId, memberId)));
	}

	@Override
	@GetMapping("/sheet/presigned")
	public ResponseEntity<SuccessResponse<ExamSheetPreSignedUrlResponseDTO>> getExamSheetPreSignedUrl() {
		PreSignedUrlVO universityExamRecordSheetPreSignedUrlVO = examRecordSheetService.getExamRecordSheetPreSignedUrl();
		return ResponseEntity.ok().body(SuccessResponse.of(
			ExamRecordSuccessType.GET_EXAM_RECORD_SHEET_PRESIGNED_SUCCESS,
			ExamSheetPreSignedUrlResponseDTO.of(universityExamRecordSheetPreSignedUrlVO.getFileName(),
				universityExamRecordSheetPreSignedUrlVO.getUrl())));
	}

	@Override
	@PostMapping("/sheet")
	public ResponseEntity<SuccessResponse<ExamRecordIdResponse>> createExamRecord(
		@Valid @RequestBody final CreateExamRecordRequestDTO request,
		@AuthMember final String memberId) {
		ExamRecordIdResponse response = null;

		if (request.editingType() == EditingType.REVISION) {
			response = examRecordService.createRevisionExamRecord(request, memberId);
		} else if (request.editingType() == EditingType.EDITING) {
			response = examRecordService.createEditingExamRecord(request, memberId);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(
			ExamRecordSuccessType.CREATE_EXAM_RECORD_SUCCESS, response
		));
	}
}
