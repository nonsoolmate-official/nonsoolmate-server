package com.nonsoolmate.nonsoolmateServer.domain.examRecord.service;

import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.CreateExamRecordRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.ExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordException;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository.ExamRecordRepository;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.exception.MemberException;
import com.nonsoolmate.nonsoolmateServer.domain.member.repository.MemberRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamException;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamRepository;
import com.nonsoolmate.nonsoolmateServer.external.aws.error.AWSClientException;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.S3Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExamRecordService {
	private final ExamRecordRepository examRecordRepository;
	private final ExamRepository examRepository;
	private final CloudFrontService cloudFrontService;
	private final S3Service s3Service;
	private final MemberRepository memberRepository;

	public ExamRecordResponseDTO getExamRecord(Long examId, Member member) {

		Exam exam = getExam(examId);
		ExamRecord examRecord = getExamByExamAndMember(exam, member);

		validateCorrection(examRecord);

		String answerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
			exam.getExamAnswerFileName());
		String resultUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
			examRecord.getExamRecordResultFileName());

		return ExamRecordResponseDTO.of(exam.getExamFullName(), answerUrl, resultUrl);
	}

	public ExamRecordResultResponseDTO getExamRecordResult(Long examId, Member member) {

		Exam exam = getExam(examId);
		ExamRecord examRecord = getExamByExamAndMember(exam, member);

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
	public ExamRecordIdResponse createExamRecord(
		final CreateExamRecordRequestDTO request, final Member member) {
		final Exam exam = getExam(request.examId());
		validateExam(exam, member);
		try {
			final String fileName = s3Service.validateURL(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
			final ExamRecord universityexamRecord = createExamRecord(exam, member,
				request.memberTakeTimeExam(),
				fileName);

			// TODO : 데모데이 이후 삭제 필요.
			String universityName = exam.getUniversity().getUniversityName();
			universityexamRecord.updateRecordResultFileName(universityName + "_첨삭.pdf");
			// TODO : 데모데이 이후 삭제 필요.

			final ExamRecord saveUniversityExamRecord = examRecordRepository.save(
				universityexamRecord);
			decreaseMemberTicketCount(member);
			return ExamRecordIdResponse.of(saveUniversityExamRecord.getExamRecordId());
		} catch (AWSClientException | MemberException e) {
			throw e;
		} catch (RuntimeException e) {
			s3Service.deleteFile(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
			throw new ExamRecordException(CREATE_EXAM_RECORD_FAIL);
		}
	}

	private void validateExam(final Exam exam, final Member member) {
		final ExamRecord existExamRecord = examRecordRepository.findByExamAndMember(
			exam, member).orElse(null);
		if (existExamRecord != null) {
			throw new ExamRecordException(ALREADY_CREATE_EXAM_RECORD);
		}
	}

	private void decreaseMemberTicketCount(final Member member) {
		try {
			member.decreaseTicket();
			memberRepository.save(member);
		} catch (MemberException e) {
			throw e;
		}
	}

	private ExamRecord createExamRecord(final Exam exam, final Member member,
		final int takeTimeExam, final String sheetFileName) {
		return ExamRecord.builder()
			.exam(exam)
			.examResultStatus(ExamResultStatus.ONGOING)
			.member(member)
			.timeTakeExam(takeTimeExam)
			.examRecordSheetFileName(sheetFileName)
			.build();
	}

	private Exam getExam(final Long examId) {
		return examRepository.findByExamId(examId)
			.orElseThrow(() -> new ExamException(INVALID_EXAM));
	}

	private ExamRecord getExamByExamAndMember(final Exam exam, final Member member) {
		return examRecordRepository.findByExamAndMemberOrElseThrowException(
			exam, member);
	}

	@Transactional
	public void promotionUpdateExamRecord(final Exam exam, final Member member){

	}
}
