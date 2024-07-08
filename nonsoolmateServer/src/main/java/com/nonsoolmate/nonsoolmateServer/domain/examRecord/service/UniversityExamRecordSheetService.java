package com.nonsoolmate.nonsoolmateServer.domain.examRecord.service;

import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.EXAM_SHEET_FOLDER_NAME;

import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.vo.PreSignedUrlVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UniversityExamRecordSheetService {
    private final CloudFrontService cloudFrontService;

    public PreSignedUrlVO getExamRecordSheetPreSignedUrl() {
        return cloudFrontService.createPreSignedPutUrl(EXAM_SHEET_FOLDER_NAME);
    }

}
