package com.nonsoolmate.nonsoolmateServer.domain.university.service;

import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamAndAnswerResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamUrlResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamImageAndAnswerResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamImageResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.ExamInfoResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.ExamImage;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamException;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamExceptionType;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamImageRepository;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamRepository;

import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExamService {
    private final ExamRepository examRepository;
    private final ExamImageRepository examImageRepository;
    private final CloudFrontService cloudFrontService;


    public ExamInfoResponseDTO getExamInfo(final Long universityExamId) {
        Exam exam = examRepository.findByExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        String examName = exam.getExamFullName();
        return ExamInfoResponseDTO.of(exam.getExamId(),
                examName,
                exam.getExamTimeLimit());
    }

    public ExamUrlResponseDTO getExamFile(final Long id) {
        Exam exam = examRepository.findByExamId(id)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        return ExamUrlResponseDTO.of(cloudFrontService.createPreSignedGetUrl(EXAM_FILE_FOLDER_NAME,
                exam.getExamFileName()));
    }

    public Page<ExamImageResponseDTO> getExamImages(final Long id, final Pageable pageable) {
        Exam exam = examRepository.findByExamId(id)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        Page<ExamImage> universityExamImages = examImageRepository.findAllByExamOrderByExamImageIdAscOrderByPageAsc(
			exam,
                pageable);
        return universityExamImages.map(image ->
                ExamImageResponseDTO.of(cloudFrontService.createPreSignedGetUrl(EXAM_IMAGE_FOLDER_NAME,
                        image.getExamImageFileName())));
    }

    public ExamImageAndAnswerResponseDTO getExamImageAndAnswer(Long universityExamId) {
        Exam exam = examRepository.findByExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));

        String examAnswerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
                exam.getExamAnswerFileName());
        List<ExamImageResponseDTO> examImageUrls = new ArrayList<>();

        List<ExamImage> examImages = examImageRepository.findAllByExamOrderByExamImageIdAsc(
			exam);

        examImages.stream().forEach(examImage -> {
            String preSignedGetUrl = cloudFrontService.createPreSignedGetUrl(EXAM_IMAGE_FOLDER_NAME,
                    examImage.getExamImageFileName());
            examImageUrls.add(
                    ExamImageResponseDTO.of(preSignedGetUrl));
        });

        return ExamImageAndAnswerResponseDTO.of(
                exam.getExamFullName()
                , examImageUrls, examAnswerUrl);
    }

    public ExamAndAnswerResponseDTO getExamAndAnswer(final Long universityExamId){
        Exam exam = examRepository.findByExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        String examUrl = cloudFrontService.createPreSignedGetUrl(EXAM_FILE_FOLDER_NAME, exam.getExamFileName());
        String universityAnswerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME, exam.getExamAnswerFileName());
        return ExamAndAnswerResponseDTO.of(
            exam.getExamFullName(),
            examUrl, universityAnswerUrl);
    }

}