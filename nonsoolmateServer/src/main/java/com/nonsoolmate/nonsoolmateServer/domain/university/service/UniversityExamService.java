package com.nonsoolmate.nonsoolmateServer.domain.university.service;

import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.*;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.UniversityExamImage;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamException;
import com.nonsoolmate.nonsoolmateServer.domain.university.exception.UniversityExamExceptionType;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.UniversityExamImageRepository;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.UniversityExamRepository;

import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniversityExamService {
    private final UniversityExamRepository universityExamRepository;
    private final UniversityExamImageRepository universityExamImageRepository;
    private final CloudFrontService cloudFrontService;


    public UniversityExamInfoResponseDTO getUniversityExamInfo(final Long universityExamId) {
        Exam exam = universityExamRepository.findByUniversityExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        String universityExamName = exam.getUniversityExamFullName();
        return UniversityExamInfoResponseDTO.of(exam.getUniversityExamId(),
                universityExamName,
                exam.getUniversityExamTimeLimit());
    }

    public UniversityExamFileResponseDTO getUniversityExamFile(final Long id) {
        Exam exam = universityExamRepository.findByUniversityExamId(id)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        return UniversityExamFileResponseDTO.of(cloudFrontService.createPreSignedGetUrl(EXAM_FILE_FOLDER_NAME,
                exam.getUniversityExamFileName()));
    }

    public Page<UniversityExamImageResponseDTO> getUniversityExamImages(final Long id, final Pageable pageable) {
        Exam exam = universityExamRepository.findByUniversityExamId(id)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        Page<UniversityExamImage> universityExamImages = universityExamImageRepository.findAllByUniversityExamOrderByPageAsc(
			exam,
                pageable);
        return universityExamImages.map(image ->
                UniversityExamImageResponseDTO.of(cloudFrontService.createPreSignedGetUrl(EXAM_IMAGE_FOLDER_NAME,
                        image.getUniversityExamImageFileName())));
    }

    public UniversityExamImageAndAnswerResponseDTO getUniversityExamImageAndAnswer(Long universityExamId) {
        Exam exam = universityExamRepository.findByUniversityExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));

        String examAnswerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
                exam.getUniversityExamAnswerFileName());
        List<UniversityExamImageResponseDTO> examImageUrls = new ArrayList<>();

        List<UniversityExamImage> UniversityExamImages = universityExamImageRepository.findAllByUniversityExamOrderByPageAsc(
			exam);

        UniversityExamImages.stream().forEach(universityExamImage -> {
            String preSignedGetUrl = cloudFrontService.createPreSignedGetUrl(EXAM_IMAGE_FOLDER_NAME,
                    universityExamImage.getUniversityExamImageFileName());
            examImageUrls.add(
                    UniversityExamImageResponseDTO.of(preSignedGetUrl));
        });

        return UniversityExamImageAndAnswerResponseDTO.of(
                exam.getUniversityExamFullName()
                , examImageUrls, examAnswerUrl);
    }

    public UniversityExamAndAnswerResponseDTO getUniversityExamAndAnswer(final long universityExamId){
        Exam exam = universityExamRepository.findByUniversityExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        String universityExamUrl = cloudFrontService.createPreSignedGetUrl(EXAM_FILE_FOLDER_NAME, exam.getUniversityExamFileName());
        String universityAnswerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME, exam.getUniversityExamAnswerFileName());
        return UniversityExamAndAnswerResponseDTO.of(exam.getUniversityExamFullName(), universityExamUrl, universityAnswerUrl);
    }

}