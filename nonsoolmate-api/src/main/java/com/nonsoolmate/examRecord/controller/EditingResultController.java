package com.nonsoolmate.examRecord.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.examRecord.controller.dto.request.UpdateExamRecordResultRequestDTO;
import com.nonsoolmate.examRecord.controller.dto.response.EditingResultDTO;
import com.nonsoolmate.examRecord.entity.enums.EditingType;
import com.nonsoolmate.examRecord.service.ExamRecordService;
import com.nonsoolmate.global.security.AuthMember;

@RestController
@RequestMapping("/college/exam")
@RequiredArgsConstructor
public class EditingResultController implements EditingResultApi {
  private final ExamRecordService examRecordService;

  @GetMapping("/{exam-id}/exam-record/result")
  public ResponseEntity<EditingResultDTO> getExamRecordResult(
      @PathVariable("exam-id") final long examId,
      @RequestParam("type") final EditingType type,
      @AuthMember final String memberId) {
    return ResponseEntity.ok()
        .body(examRecordService.getExamRecordEditingResult(examId, type, memberId));
  }

  // for admin
  @PatchMapping("/result")
  public ResponseEntity<EditingResultDTO> updateExamRecordResult(
      @Valid @RequestBody final UpdateExamRecordResultRequestDTO request) {
    return ResponseEntity.ok().body(examRecordService.updateExamRecordEditingResult(request));
  }
}
