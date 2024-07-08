package com.nonsoolmate.nonsoolmateServer.domain.examRecord.service;

import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamExceptionType.INVALID_EXAM;
import static com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.exception.MemberException;
import com.nonsoolmate.nonsoolmateServer.domain.member.repository.MemberRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.ExamException;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamRepository;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.response.UniversityExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.CreateUniversityExamRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.exception.ExamRecordException;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository.UniversityExamRecordRepository;
import com.nonsoolmate.nonsoolmateServer.external.aws.error.AWSClientException;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniversityExamRecordService {
    private final UniversityExamRecordRepository universityExamRecordRepository;
    private final ExamRepository examRepository;
    private final CloudFrontService cloudFrontService;
    private final S3Service s3Service;
    private final MemberRepository memberRepository;

    public UniversityExamRecordResponseDTO getExamRecord(Long universityExamId, Member member) {

        Exam exam = getUniversityExam(universityExamId);
        ExamRecord examRecord = getUniversityExamByUniversityExamAndMember(exam, member);

        validateCorrection(examRecord);

        String answerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
                exam.getExamAnswerFileName());
        String resultUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
                examRecord.getExamRecordResultFileName());

        return UniversityExamRecordResponseDTO.of(exam.getExamFullName(), answerUrl, resultUrl);
    }

    public UniversityExamRecordResultResponseDTO getExamRecordResult(Long universityExamId, Member member) {

        Exam exam = getUniversityExam(universityExamId);
        ExamRecord examRecord = getUniversityExamByUniversityExamAndMember(exam, member);

        validateCorrection(examRecord);

        String resultUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
                examRecord.getExamRecordResultFileName());

        return UniversityExamRecordResultResponseDTO.of(resultUrl);
    }

    private void validateCorrection(ExamRecord examRecord) {
        if(examRecord.getExamRecordResultFileName() == null){
            throw new ExamRecordException(INVALID_EXAM_RECORD_RESULT_FILE_NAME);
        }
    }

    @Transactional
    public UniversityExamRecordIdResponse createExamRecord(
            final CreateUniversityExamRequestDTO request, final Member member) {
        final Exam exam = getUniversityExam(request.universityExamId());
        validateUniversityExam(exam, member);
        try {
            final String fileName = s3Service.validateURL(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
            final ExamRecord universityexamRecord = createExamRecord(exam, member,
                    request.memberTakeTimeExam(),
                    fileName);
            final ExamRecord saveUniversityExamRecord = universityExamRecordRepository.save(
                    universityexamRecord);
            decreaseMemberTicketCount(member);
            return UniversityExamRecordIdResponse.of(saveUniversityExamRecord.getExamRecordId());
        } catch (AWSClientException | MemberException e) {
            throw e;
        } catch (RuntimeException e) {
            s3Service.deleteFile(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
            throw new ExamRecordException(CREATE_EXAM_RECORD_FAIL);
        }
    }

    private void validateUniversityExam(final Exam exam, final Member member){
        final ExamRecord existExamRecord = universityExamRecordRepository.findByUniversityExamAndMember(
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

    private Exam getUniversityExam(final Long universityExamId) {
        return examRepository.findByExamId(universityExamId)
                .orElseThrow(() -> new ExamException(INVALID_EXAM));
    }

    private ExamRecord getUniversityExamByUniversityExamAndMember(final Exam exam,
                                                                            final Member member) {
        return universityExamRecordRepository.findByUniversityExamAndMemberOrElseThrowException(
            exam, member);
    }
}
