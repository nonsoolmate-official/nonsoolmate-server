package com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.service;

import static com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM;
import static com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.exception.UniversityExamRecordExceptionType.*;
import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.member.exception.MemberException;
import com.nonsoolmate.nonsoolmateServer.domain.member.repository.MemberRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamException;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.UniversityExamRepository;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.controller.dto.response.UniversityExamRecordIdResponse;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.controller.dto.response.UniversityExamRecordResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.controller.dto.response.UniversityExamRecordResultResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.controller.dto.request.CreateUniversityExamRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.entity.UniversityExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.entity.enums.ExamResultStatus;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.exception.UniversityExamRecordException;
import com.nonsoolmate.nonsoolmateServer.domain.universityExamRecord.repository.UniversityExamRecordRepository;
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
    private final UniversityExamRepository universityExamRepository;
    private final CloudFrontService cloudFrontService;
    private final S3Service s3Service;
    private final MemberRepository memberRepository;

    public UniversityExamRecordResponseDTO getUniversityExamRecord(Long universityExamId, Member member) {

        Exam exam = getUniversityExam(universityExamId);
        UniversityExamRecord universityExamRecord = getUniversityExamByUniversityExamAndMember(exam, member);

        validateCorrection(universityExamRecord);

        String answerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
                exam.getExamAnswerFileName());
        String resultUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
                universityExamRecord.getExamRecordResultFileName());

        return UniversityExamRecordResponseDTO.of(exam.getExamFullName(), answerUrl, resultUrl);
    }

    public UniversityExamRecordResultResponseDTO getUniversityExamRecordResult(Long universityExamId, Member member) {

        Exam exam = getUniversityExam(universityExamId);
        UniversityExamRecord universityExamRecord = getUniversityExamByUniversityExamAndMember(exam, member);

        validateCorrection(universityExamRecord);

        String resultUrl = cloudFrontService.createPreSignedGetUrl(EXAM_RESULT_FOLDER_NAME,
                universityExamRecord.getExamRecordResultFileName());

        return UniversityExamRecordResultResponseDTO.of(resultUrl);
    }

    private void validateCorrection(UniversityExamRecord universityExamRecord) {
        if(universityExamRecord.getExamRecordResultFileName() == null){
            throw new UniversityExamRecordException(INVALID_UNIVERSITY_EXAM_RECORD_RESULT_FILE_NAME);
        }
    }

    @Transactional
    public UniversityExamRecordIdResponse createUniversityExamRecord(
            final CreateUniversityExamRequestDTO request, final Member member) {
        final Exam exam = getUniversityExam(request.universityExamId());
        validateUniversityExam(exam, member);
        try {
            final String fileName = s3Service.validateURL(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
            final UniversityExamRecord universityexamRecord = createUniversityExamRecord(exam, member,
                    request.memberTakeTimeExam(),
                    fileName);
            final UniversityExamRecord saveUniversityUniversityExamRecord = universityExamRecordRepository.save(
                    universityexamRecord);
            decreaseMemberTicketCount(member);
            return UniversityExamRecordIdResponse.of(saveUniversityUniversityExamRecord.getUniversityExamRecordId());
        } catch (AWSClientException | MemberException e) {
            throw e;
        } catch (RuntimeException e) {
            s3Service.deleteFile(EXAM_SHEET_FOLDER_NAME, request.memberSheetFileName());
            throw new UniversityExamRecordException(CREATE_UNIVERSITY_EXAM_RECORD_FAIL);
        }
    }

    private void validateUniversityExam(final Exam exam, final Member member){
        final UniversityExamRecord existUniversityExamRecord = universityExamRecordRepository.findByUniversityExamAndMember(
            exam, member).orElse(null);
        if (existUniversityExamRecord != null) {
            throw new UniversityExamRecordException(ALREADY_CREATE_EXAM_RECORD);
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

    private UniversityExamRecord createUniversityExamRecord(final Exam exam, final Member member,
                                                            final int takeTimeExam, final String sheetFileName) {
        return UniversityExamRecord.builder()
                .universityExam(exam)
                .examResultStatus(ExamResultStatus.ONGOING)
                .member(member)
                .timeTakeExam(takeTimeExam)
                .examRecordSheetFileName(sheetFileName)
                .build();
    }

    private Exam getUniversityExam(final Long universityExamId) {
        return universityExamRepository.findByUniversityExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(INVALID_UNIVERSITY_EXAM));
    }

    private UniversityExamRecord getUniversityExamByUniversityExamAndMember(final Exam exam,
                                                                            final Member member) {
        return universityExamRecordRepository.findByUniversityExamAndMemberOrElseThrowException(
            exam, member);
    }
}
