package com.nonsoolmate.nonsoolmateServer.domain.examRecord.service;

import static com.nonsoolmate.nonsoolmateServer.external.aws.FolderName.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.external.aws.service.CloudFrontService;
import com.nonsoolmate.nonsoolmateServer.external.aws.service.vo.PreSignedUrlVO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExamRecordSheetService {
	private final CloudFrontService cloudFrontService;

	public PreSignedUrlVO getExamRecordSheetPreSignedUrl() {
		return cloudFrontService.createPreSignedPutUrl(EXAM_SHEET_FOLDER_NAME);
	}

}
