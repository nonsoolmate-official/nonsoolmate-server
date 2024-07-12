package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordSuccessType.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.CreateExamRecordRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamSheetPreSignedUrlResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordSuccessType;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.service.ExamRecordService;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.service.ExamRecordSheetService;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.vo.PreSignedUrlVO;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/university/exam-record")
@RequiredArgsConstructor
public class ExamRecordController implements ExamRecordApi {

	private final ExamRecordService examRecordService;
	private final ExamRecordSheetService examRecordSheetService;

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<SuccessResponse<ExamRecordResponseDTO>> getExamRecord(
		@PathVariable("id") Long examId, @AuthUser Member member) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_EXAM_RECORD_SUCCESS,
			examRecordService.getExamRecord(examId, member)));
	}

	@Override
	@GetMapping("/result/{id}")
	public ResponseEntity<SuccessResponse<ExamRecordResultResponseDTO>> getExamRecordResult(
		@PathVariable("id") Long examId, @AuthUser Member member) {
		return ResponseEntity.ok().body(SuccessResponse.of(GET_EXAM_RECORD_RESULT_SUCCESS,
			examRecordService.getExamRecordResult(examId, member)));
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
		@Valid @RequestBody final CreateExamRecordRequestDTO createExamRecordRequestDTO,
		@AuthUser final Member member) {
		return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(
			ExamRecordSuccessType.CREATE_EXAM_RECORD_SUCCESS,
			examRecordService.createExamRecord(
				createExamRecordRequestDTO, member)));
	}
}
