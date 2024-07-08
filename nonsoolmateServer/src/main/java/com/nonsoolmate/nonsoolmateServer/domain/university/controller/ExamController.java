package com.nonsoolmate.nonsoolmateServer.domain.university.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamSuccessType.GET_UNIVERSITY_EXAM_AND_ANSWER_SUCCESS;
import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamSuccessType.GET_UNIVERSITY_EXAM_IMAGE_AND_ANSWER_SUCCESS;

import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.*;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamSuccessType;
import com.nonsoolmate.nonsoolmateServer.domain.university.service.ExamService;
import com.nonsoolmate.nonsoolmateServer.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/university/exam")
@RequiredArgsConstructor
public class ExamController implements ExamApi{
    private final ExamService examService;

    @Override
    @GetMapping("/{id}/info")
    public ResponseEntity<SuccessResponse<UniversityExamInfoResponseDTO>> getUniversityExamInfo(
            @PathVariable("id") final Long universityExamId) {
        return ResponseEntity.ok().body(SuccessResponse.of(UniversityExamSuccessType.GET_UNIVERSITY_EXAM_SUCCESS,
                examService.getUniversityExamInfo(universityExamId)));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<UniversityExamFileResponseDTO>> getUniversityExamFile(
            @PathVariable("id") final Long id) {
        return ResponseEntity.ok().body(SuccessResponse.of(UniversityExamSuccessType.GET_UNIVERSITY_EXAM_FILE_SUCCESS,
                examService.getUniversityExamFile(id)));
    }

    @Override
    @GetMapping("{id}/image")
    public ResponseEntity<SuccessResponse<Page<UniversityExamImageResponseDTO>>> getUniversityExamImages(
            @PathVariable("id") final Long id, @RequestParam(value="page", defaultValue = "0") final int page, final Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(page, 1);
        Page<UniversityExamImageResponseDTO> images = examService.getUniversityExamImages(id, pageRequest);
        return ResponseEntity.ok()
                .body(SuccessResponse.of(UniversityExamSuccessType.GET_UNIVERSITY_EXAM_IMAGE_SUCCESS, images));
    }

    @Override
    @GetMapping("{id}/answer")
    public ResponseEntity<SuccessResponse<UniversityExamImageAndAnswerResponseDTO>> getUniversityExamImageAndAnswer(
            @PathVariable("id") Long universityExamId
    ) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_UNIVERSITY_EXAM_IMAGE_AND_ANSWER_SUCCESS,
                examService.getUniversityExamImageAndAnswer(universityExamId)));
    }

    // TODO: 이미지 조회에서 PDF 조회로 변경되면 mapping 경로 변경
    @Override
    @GetMapping("/v2/{id}/answer")
    public ResponseEntity<SuccessResponse<UniversityExamAndAnswerResponseDTO>> getUniversityExamAndAnswer(
            @PathVariable("id") Long examId
    ) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_UNIVERSITY_EXAM_IMAGE_AND_ANSWER_SUCCESS,
            examService.getUniversityExamAndAnswer(examId)));
    }
}
