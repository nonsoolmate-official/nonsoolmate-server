package com.nonsoolmate.nonsoolmateServer.domain.university.controller;

import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamSuccessType.GET_UNIVERSITY_EXAM_IMAGE_AND_ANSWER_SUCCESS;

import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamFileResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamImageAndAnswerResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamImageResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamInfoResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamSuccessType;
import com.nonsoolmate.nonsoolmateServer.domain.university.service.UniversityExamService;
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
public class UniversityExamController implements UniversityApi{
    private final UniversityExamService universityExamService;

    @Override
    @GetMapping("/{id}/info")
    public ResponseEntity<SuccessResponse<UniversityExamInfoResponseDTO>> getUniversityExamInfo(
            @PathVariable("id") final Long universityExamId) {
        return ResponseEntity.ok().body(SuccessResponse.of(UniversityExamSuccessType.GET_UNIVERSITY_EXAM_SUCCESS,
                universityExamService.getUniversityExamInfo(universityExamId)));
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<UniversityExamFileResponseDTO>> getUniversityExamFile(
            @PathVariable("id") final Long id) {
        return ResponseEntity.ok().body(SuccessResponse.of(UniversityExamSuccessType.GET_UNIVERSITY_EXAM_FILE_SUCCESS,
                universityExamService.getUniversityExamFile(id)));
    }

    @Override
    @GetMapping("{id}/image")
    public ResponseEntity<SuccessResponse<Page<UniversityExamImageResponseDTO>>> getUniversityExamImages(
            @PathVariable("id") final Long id, @RequestParam(defaultValue = "0") final int page, final Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(page, 1);
        Page<UniversityExamImageResponseDTO> images = universityExamService.getUniversityExamImages(id, pageRequest);
        return ResponseEntity.ok()
                .body(SuccessResponse.of(UniversityExamSuccessType.GET_UNIVERSITY_EXAM_IMAGE_SUCCESS, images));
    }

    @Override
    @GetMapping("{id}/answer")
    public ResponseEntity<SuccessResponse<UniversityExamImageAndAnswerResponseDTO>> getUniversityExamImageAndAnswer(
            @PathVariable("id") Long universityExamId
    ) {
        return ResponseEntity.ok().body(SuccessResponse.of(GET_UNIVERSITY_EXAM_IMAGE_AND_ANSWER_SUCCESS,
                universityExamService.getUniversityExamImageAndAnswer(universityExamId)));
    }
}
