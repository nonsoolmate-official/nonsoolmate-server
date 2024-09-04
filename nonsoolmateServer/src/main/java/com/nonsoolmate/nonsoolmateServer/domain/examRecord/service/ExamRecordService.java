package com.nonsoolmate.nonsoolmateServer.domain.examRecord.service;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.CreateExamRecordRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.EditingResultDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.EditingType;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordException;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository.ExamRecordRepository;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.exception.MemberException;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamException;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamRepository;
import com.nonsoolmate.nonsoolmateServer.external.aws.error.AWSClientException;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.S3Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ExamRecordService {
	private final ExamRecordRepository examRecordRepository;
	private final ExamRepository examRepository;
	private final CloudFrontService cloudFrontService;
	private final S3Service s3Service;

	public ExamRecordResponseDTO getExamRecord(Long examId, Member member) {

		Exam exam = getExam(examId);
		List<ExamRecord> examRecord = getExamByExamAndMember(exam, member);

		validateCorrection(examRecord.get(0));

		String answerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
			exam.getExamAnswerFileName());
		String resultUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
			examRecord.get(0).getExamRecordResultFileName());

		return ExamRecordResponseDTO.of(exam.getExamFullName(), answerUrl, resultUrl);
	}

	public ExamRecordResultResponseDTO getExamRecordResult(Long examId, Member member) {

		Exam exam = getExam(examId);
		ExamRecord examRecord = getExamByExamAndMember(exam, member).get(0);

		validateCorrection(examRecord);

		String resultUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
			examRecord.getExamRecordResultFileName());

		return ExamRecordResultResponseDTO.of(resultUrl);
	}

	private void validateCorrection(ExamRecord examRecord) {
		if (examRecord.getExamRecordResultFileName() == null) {
			throw new ExamRecordException(INVALID_EXAM_RECORD_RESULT_FILE_NAME);
		}
	}

	@Transactional
	public ExamRecordIdResponse createEditingExamRecord(
		final CreateExamRecordRequestDTO request, final Member member) {
		final Exam exam = getExam(request.examId());
		validateExam(exam, member, EditingType.EDITING);

		try {
			final ExamRecord savedExamRecord = processAndSaveExamRecord(request, member, exam, EditingType.EDITING);
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
		final CreateExamRecordRequestDTO request, final Member member) {
		final Exam exam = getExam(request.examId());

		validateExistEditingExamRecord(exam, member);
		validateExam(exam, member, EditingType.REVISION);

		try {
			final ExamRecord savedExamRecord = processAndSaveExamRecord(request, member, exam, EditingType.REVISION);
			member.decreaseReReviewTicket();
			return ExamRecordIdResponse.of(savedExamRecord.getExamRecordId());
		} catch (AWSClientException | MemberException e) {
			throw e;
		} catch (RuntimeException e) {
			s3Service.deleteFile(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
			throw new ExamRecordException(CREATE_EXAM_RECORD_FAIL);
		}
	}

	void validateExistEditingExamRecord(final Exam exam, final Member member) {
		ExamRecord examRecord = examRecordRepository.findByExamAndMemberAndEditingType(exam, member,
			EditingType.EDITING).orElseThrow(() -> new ExamRecordException(INVALID_CREATE_REVISION_EXAM_RECORD));
		if (examRecord.getExamResultStatus() == ExamResultStatus.REVIEW_ONGOING) {
			throw new ExamRecordException(INVALID_CREATE_REVISION_EXAM_RECORD);
		}
	}

	private ExamRecord processAndSaveExamRecord(final CreateExamRecordRequestDTO request, final Member member,
		final Exam exam, final EditingType editingType) {
		final String fileName = s3Service.validateURL(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
		final ExamRecord examRecord = createExamRecord(exam, member, request.memberTakeTimeExam(), fileName,
			editingType);
		return examRecordRepository.save(examRecord);
	}

	private void validateExam(final Exam exam, final Member member, final EditingType editingType) {
		ExamRecord examRecord = examRecordRepository.findByExamAndMemberAndEditingType(exam, member,
			editingType).orElse(null);
		if (examRecord != null) {
			throw new ExamRecordException(ALREADY_CREATE_EXAM_RECORD);
		}
	}

	private ExamRecord createExamRecord(final Exam exam, final Member member,
		final int takeTimeExam, final String sheetFileName, final EditingType editingType) {
		ExamResultStatus examResultStatus =
			editingType == EditingType.EDITING ? ExamResultStatus.REVIEW_ONGOING : ExamResultStatus.RE_REVIEW_ONGOING;
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
		return examRepository.findByExamId(examId)
			.orElseThrow(() -> new ExamException(INVALID_EXAM));
	}

	private List<ExamRecord> getExamByExamAndMember(final Exam exam, final Member member) {
		return examRecordRepository.findByExamAndMember(exam, member);
	}

	public EditingResultDTO getExamRecordEditingResult(final long examId, final EditingType type, final Member member) {
		ExamRecord examRecord = examRecordRepository.findByExamAndMemberAndEditingTypeOrThrow(examId, type,
			member.getMemberId());
		String examResultFileUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
			examRecord.getExamRecordResultFileName());
		return EditingResultDTO.of(type, examRecord.getExamResultStatus().getStatus(),
			examResultFileUrl);
	}
}
