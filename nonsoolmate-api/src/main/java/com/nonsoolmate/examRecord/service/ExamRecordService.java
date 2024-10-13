package com.nonsoolmate.examRecord.service;

import static com.nonsoolmate.aws.FolderName.*;
import static com.nonsoolmate.exception.examRecord.ExamRecordExceptionType.*;
import static com.nonsoolmate.exception.university.ExamExceptionType.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.aws.service.CloudFrontService;
import com.nonsoolmate.aws.service.S3Service;
import com.nonsoolmate.examRecord.controller.dto.request.CreateExamRecordRequestDTO;
import com.nonsoolmate.examRecord.controller.dto.request.UpdateExamRecordResultRequestDTO;
import com.nonsoolmate.examRecord.controller.dto.response.EditingResultDTO;
import com.nonsoolmate.examRecord.controller.dto.response.ExamRecordIdResponse;
import com.nonsoolmate.examRecord.entity.ExamRecord;
import com.nonsoolmate.examRecord.entity.enums.EditingType;
import com.nonsoolmate.examRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.examRecord.repository.ExamRecordRepository;
import com.nonsoolmate.exception.aws.AWSClientException;
import com.nonsoolmate.exception.examRecord.ExamRecordException;
import com.nonsoolmate.exception.member.MemberException;
import com.nonsoolmate.exception.university.ExamException;
import com.nonsoolmate.global.event.EmailEventListener;
import com.nonsoolmate.global.event.ExamRecordStatusUpdatedEvent;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.university.entity.Exam;
import com.nonsoolmate.university.repository.ExamRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ExamRecordService {
  private final ExamRecordRepository examRecordRepository;
  private final ExamRepository examRepository;
  private final MemberRepository memberRepository;

  private final CloudFrontService cloudFrontService;
  private final S3Service s3Service;
  private final EmailEventListener emailEventListener;

  private static final String EXAM_RECORD_RESULT_FILE_NAME_EMPTY = "";

  @Transactional
  public ExamRecordIdResponse createEditingExamRecord(
      final CreateExamRecordRequestDTO request, final String memberId) {

    Member member = memberRepository.findByMemberIdOrThrow(memberId);

    final Exam exam = getExam(request.examId());
    validateExam(exam, member, EditingType.EDITING);

    try {
      final ExamRecord savedExamRecord =
          processAndSaveExamRecord(request, member, exam, EditingType.EDITING);
      member.decreaseReviewTicket();
      return ExamRecordIdResponse.of(savedExamRecord.getExamRecordId());
    } catch (AWSClientException | MemberException e) {
      throw e;
    } catch (RuntimeException e) {
      s3Service.deleteFile(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
      throw new ExamRecordException(CREATE_EXAM_RECORD_FAIL);
    }
  }

  @Transactional
  public ExamRecordIdResponse createRevisionExamRecord(
      final CreateExamRecordRequestDTO request, final String memberId) {

    Member member = memberRepository.findByMemberIdOrThrow(memberId);

    final Exam exam = getExam(request.examId());

    validateExistEditingExamRecord(exam, member);
    validateExam(exam, member, EditingType.REVISION);

    try {
      final ExamRecord savedExamRecord =
          processAndSaveExamRecord(request, member, exam, EditingType.REVISION);
      member.decreaseReReviewTicket();
      return ExamRecordIdResponse.of(savedExamRecord.getExamRecordId());
    } catch (AWSClientException | MemberException e) {
      throw e;
    } catch (RuntimeException e) {
      s3Service.deleteFile(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
      throw new ExamRecordException(CREATE_EXAM_RECORD_FAIL);
    }
  }

  @Transactional
  public EditingResultDTO updateExamRecordEditingResult(
      final UpdateExamRecordResultRequestDTO request) {
    ExamRecord examRecord =
        examRecordRepository.findByExamAndMemberAndEditingTypeOrThrow(
            request.examId(), request.editingType(), request.memberId());
    examRecord.updateExamRecordResultFileName(request.examResultFileName());

    boolean isReviewOngoing = examRecord.getExamResultStatus() == ExamResultStatus.REVIEW_ONGOING;

    if (isReviewOngoing) {
      examRecord.updateExamResultStatus(ExamResultStatus.REVIEW_FINISH);
    } else {
      examRecord.updateExamResultStatus(ExamResultStatus.RE_REVIEW_FINISH);
    }

    String email = examRecord.getMember().getEmail();
    String editingType = examRecord.getEditingType().getType();
    String examFullName = examRecord.getExam().getExamFullName();

    emailEventListener.publishExamRecordStatusUpdatedEvent(
        ExamRecordStatusUpdatedEvent.of(email, editingType, examFullName));

    return EditingResultDTO.of(
        request.editingType(),
        examRecord.getExamResultStatus().getStatus(),
        getExamResultFileUrl(examRecord.getExamRecordResultFileName()));
  }

  void validateExistEditingExamRecord(final Exam exam, final Member member) {
    ExamRecord examRecord =
        examRecordRepository
            .findByExamAndMemberAndEditingType(exam, member, EditingType.EDITING)
            .orElseThrow(() -> new ExamRecordException(INVALID_CREATE_REVISION_EXAM_RECORD));
    if (examRecord.getExamResultStatus() == ExamResultStatus.REVIEW_ONGOING) {
      throw new ExamRecordException(INVALID_CREATE_REVISION_EXAM_RECORD);
    }
  }

  private ExamRecord processAndSaveExamRecord(
      final CreateExamRecordRequestDTO request,
      final Member member,
      final Exam exam,
      final EditingType editingType) {
    final String fileName =
        s3Service.validateURL(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
    final ExamRecord examRecord =
        createExamRecord(exam, member, request.memberTakeTimeExam(), fileName, editingType);
    return examRecordRepository.save(examRecord);
  }

  private void validateExam(final Exam exam, final Member member, final EditingType editingType) {
    ExamRecord examRecord =
        examRecordRepository
            .findByExamAndMemberAndEditingType(exam, member, editingType)
            .orElse(null);
    if (examRecord != null) {
      throw new ExamRecordException(ALREADY_CREATE_EXAM_RECORD);
    }
  }

  private ExamRecord createExamRecord(
      final Exam exam,
      final Member member,
      final int takeTimeExam,
      final String sheetFileName,
      final EditingType editingType) {
    ExamResultStatus examResultStatus =
        editingType == EditingType.EDITING
            ? ExamResultStatus.REVIEW_ONGOING
            : ExamResultStatus.RE_REVIEW_ONGOING;
    return ExamRecord.builder()
        .exam(exam)
        .member(member)
        .editingType(editingType)
        .timeTakeExam(takeTimeExam)
        .examResultStatus(examResultStatus)
        .examRecordSheetFileName(sheetFileName)
        .build();
  }

  private Exam getExam(final Long examId) {
    return examRepository.findByExamId(examId).orElseThrow(() -> new ExamException(INVALID_EXAM));
  }

  public EditingResultDTO getExamRecordEditingResult(
      final long examId, final EditingType editingType, final String memberId) {
    ExamRecord examRecord =
        examRecordRepository.findByExamAndMemberAndEditingTypeOrThrow(
            examId, editingType, memberId);
    String examResultFileUrl = getExamResultFileUrl(examRecord.getExamRecordResultFileName());
    return EditingResultDTO.of(
        editingType, examRecord.getExamResultStatus().getStatus(), examResultFileUrl);
  }

  private String getExamResultFileUrl(final String examRecordResultFileName) {
    if (examRecordResultFileName == null) {
      return EXAM_RECORD_RESULT_FILE_NAME_EMPTY;
    }
    return cloudFrontService.createPreSignedGetUrl(
        EXAM_RESULT_FOLDER_NAME, examRecordResultFileName);
  }
}
