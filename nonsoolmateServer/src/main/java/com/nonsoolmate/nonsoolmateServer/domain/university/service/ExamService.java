package com.nonsoolmate.nonsoolmateServer.domain.university.service;

import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamAndAnswerResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamInfoResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamUrlResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamException;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamRepository;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExamService {
	private final ExamRepository examRepository;
	private final CloudFrontService cloudFrontService;

	public ExamInfoResponseDTO getExamInfo(final Long examId) {
		Exam exam = getExam(examId);
		String examName = exam.getExamFullName();
		return ExamInfoResponseDTO.of(exam.getExamId(),
			examName,
			exam.getExamTimeLimit());
	}

	public ExamUrlResponseDTO getExamFile(final Long examId) {
		Exam exam = getExam(examId);
		return ExamUrlResponseDTO.of(cloudFrontService.createPreSignedGetUrl(EXAM_FILE_FOLDER_NAME,
			exam.getExamFileName()));
	}

	public ExamAndAnswerResponseDTO getExamAndAnswer(final Long examId) {
		Exam exam = getExam(examId);
		String examUrl = cloudFrontService.createPreSignedGetUrl(EXAM_FILE_FOLDER_NAME, exam.getExamFileName());
		String universityAnswerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
			exam.getExamAnswerFileName());
		return ExamAndAnswerResponseDTO.of(
			exam.getExamFullName(),
			examUrl, universityAnswerUrl);
	}

	private Exam getExam(final Long examId) {
		return examRepository.findByExamId(examId)
			.orElseThrow(() -> new ExamException(INVALID_EXAM));
	}

}