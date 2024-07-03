package com.nonsoolmate.nonsoolmateServer.domain.university.service;

import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.EXAM_ANSWER_FOLDER_NAME;
import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.EXAM_IMAGE_FOLDER_NAME;

import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamImageAndAnswerResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamImageResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.controller.dto.response.UniversityExamInfoResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.UniversityExam;
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

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniversityExamService {
    private final UniversityExamRepository universityExamRepository;
    private final UniversityExamImageRepository universityExamImageRepository;
    private final CloudFrontService cloudFrontService;


    public UniversityExamInfoResponseDTO getUniversityExamInfo(final Long universityExamId) {
        UniversityExam universityExam = universityExamRepository.findByUniversityExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        String universityExamName = universityExam.getUniversityExamFullName();
        return UniversityExamInfoResponseDTO.of(universityExam.getUniversityExamId(),
                universityExamName,
                universityExam.getUniversityExamTimeLimit());
    }

    public Page<UniversityExamImageResponseDTO> getUniversityExamImages(final Long id, final Pageable pageable) {
        UniversityExam universityExam = universityExamRepository.findByUniversityExamId(id)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));
        Page<UniversityExamImage> universityExamImages = universityExamImageRepository.findAllByUniversityExamOrderByPageAsc(
                universityExam,
                pageable);
        return universityExamImages.map(image ->
                UniversityExamImageResponseDTO.of(cloudFrontService.createPreSignedGetUrl(EXAM_IMAGE_FOLDER_NAME,
                        image.getUniversityExamImageFileName())));
    }

    public UniversityExamImageAndAnswerResponseDTO getUniversityExamImageAndAnswer(Long universityExamId) {
        UniversityExam universityExam = universityExamRepository.findByUniversityExamId(universityExamId)
                .orElseThrow(() -> new UniversityExamException(
                        UniversityExamExceptionType.INVALID_UNIVERSITY_EXAM));

        String examAnswerUrl = cloudFrontService.createPreSignedGetUrl(EXAM_ANSWER_FOLDER_NAME,
                universityExam.getUniversityExamAnswerFileName());
        List<UniversityExamImageResponseDTO> examImageUrls = new ArrayList<>();

        List<UniversityExamImage> UniversityExamImages = universityExamImageRepository.findAllByUniversityExamOrderByPageAsc(
                universityExam);

        UniversityExamImages.stream().forEach(universityExamImage -> {
            String preSignedGetUrl = cloudFrontService.createPreSignedGetUrl(EXAM_IMAGE_FOLDER_NAME,
                    universityExamImage.getUniversityExamImageFileName());
            examImageUrls.add(
                    UniversityExamImageResponseDTO.of(preSignedGetUrl));
        });

        return UniversityExamImageAndAnswerResponseDTO.of(
                universityExam.getUniversityExamFullName()
                , examImageUrls, examAnswerUrl);
    }

}