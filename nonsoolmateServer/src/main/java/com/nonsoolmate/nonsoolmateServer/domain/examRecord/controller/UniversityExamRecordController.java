package com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.UniversityExamRecordSuccessType.GET_UNIVERSITY_EXAM_RECORD_RESULT_SUCCESS;
import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.UniversityExamRecordSuccessType.GET_UNIVERSITY_EXAM_RECORD_SUCCESS;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamSheetPreSignedUrlResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.CreateUniversityExamRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.UniversityExamRecordSuccessType;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.service.UniversityExamRecordService;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.service.UniversityExamRecordSheetService;
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
public class UniversityExamRecordController implements UniversityExamRecordApi {

    private final UniversityExamRecordService universityExamRecordService;
    private final UniversityExamRecordSheetService universityExamRecordSheetService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UniversityExamRecordResponseDTO>> getUniversityExamRecord(
            @PathVariable("id") Long universityExamId, @AuthUser Member member) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_UNIVERSITY_EXAM_RECORD_SUCCESS,
                universityExamRecordService.getUniversityExamRecord(universityExamId, member)));
    }

    @Override
    @GetMapping("/result/{id}")
    public ResponseEntity<SuccessResponse<UniversityExamRecordResultResponseDTO>> getUniversityExamRecordResult(
            @PathVariable("id") Long universityExamId, @AuthUser Member member) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_UNIVERSITY_EXAM_RECORD_RESULT_SUCCESS,
                universityExamRecordService.getUniversityExamRecordResult(universityExamId, member)));
    }

    @Override
    @GetMapping("/sheet/presigned")
    public ResponseEntity<SuccessResponse<UniversityExamSheetPreSignedUrlResponseDTO>> getUniversityExamSheetPreSignedUrl() {
        PreSignedUrlVO universityExamRecordSheetPreSignedUrlVO = universityExamRecordSheetService.getUniversityExamRecordSheetPreSignedUrl();
        return ResponseEntity.ok().body(SuccessResponse.of(
                UniversityExamRecordSuccessType.GET_UNIVERSITY_EXAM_RECORD_SHEET_PRESIGNED_SUCCESS,
                UniversityExamSheetPreSignedUrlResponseDTO.of(universityExamRecordSheetPreSignedUrlVO.getFileName(),
                        universityExamRecordSheetPreSignedUrlVO.getUrl())));
    }

    @Override
    @PostMapping("/sheet")
    public ResponseEntity<SuccessResponse<UniversityExamRecordIdResponse>> createUniversityExamRecord(
            @Valid @RequestBody final CreateUniversityExamRequestDTO createUniversityExamRequestDTO,
            @AuthUser final Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(
                UniversityExamRecordSuccessType.CREATE_UNIVERSITY_EXAM_RECORD_SUCCESS,
                universityExamRecordService.createUniversityExamRecord(
                        createUniversityExamRequestDTO, member)));
    }
}
