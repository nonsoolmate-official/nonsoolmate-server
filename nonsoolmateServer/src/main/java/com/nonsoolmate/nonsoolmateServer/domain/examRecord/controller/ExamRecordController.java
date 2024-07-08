package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordSuccessType.GET_EXAM_RECORD_RESULT_SUCCESS;
import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordSuccessType.GET_EXAM_RECORD_SUCCESS;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamSheetPreSignedUrlResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.CreateUniversityExamRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordSuccessType;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.service.ExamRecordService;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.service.ExamRecordSheetService;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.vo.PreSignedUrlVO;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/university/exam-record")
@RequiredArgsConstructor
public class ExamRecordController implements ExamRecordApi {

    private final ExamRecordService examRecordService;
    private final ExamRecordSheetService examRecordSheetService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UniversityExamRecordResponseDTO>> getExamRecord(
            @PathVariable("id") Long universityExamId, @AuthUser Member member) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_EXAM_RECORD_SUCCESS,
                examRecordService.getExamRecord(universityExamId, member)));
    }

    @Override
    @GetMapping("/result/{id}")
    public ResponseEntity<SuccessResponse<UniversityExamRecordResultResponseDTO>> getExamRecordResult(
            @PathVariable("id") Long universityExamId, @AuthUser Member member) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_EXAM_RECORD_RESULT_SUCCESS,
                examRecordService.getExamRecordResult(universityExamId, member)));
    }

    @Override
    @GetMapping("/sheet/presigned")
    public ResponseEntity<SuccessResponse<UniversityExamSheetPreSignedUrlResponseDTO>> getExamSheetPreSignedUrl() {
        PreSignedUrlVO universityExamRecordSheetPreSignedUrlVO = examRecordSheetService.getExamRecordSheetPreSignedUrl();
        return ResponseEntity.ok().body(SuccessResponse.of(
                ExamRecordSuccessType.GET_EXAM_RECORD_SHEET_PRESIGNED_SUCCESS,
                UniversityExamSheetPreSignedUrlResponseDTO.of(universityExamRecordSheetPreSignedUrlVO.getFileName(),
                        universityExamRecordSheetPreSignedUrlVO.getUrl())));
    }

    @Override
    @PostMapping("/sheet")
    public ResponseEntity<SuccessResponse<UniversityExamRecordIdResponse>> createExamRecord(
            @Valid @RequestBody final CreateUniversityExamRequestDTO createUniversityExamRequestDTO,
            @AuthUser final Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(
                ExamRecordSuccessType.CREATE_EXAM_RECORD_SUCCESS,
                examRecordService.createExamRecord(
                        createUniversityExamRequestDTO, member)));
    }
}
