package com.nonsoolmate.examRecord.service;

import static com.nonsoolmate.aws.FolderName.*;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.aws.service.CloudFrontService;
import com.nonsoolmate.aws.service.vo.PreSignedUrlVO;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ExamRecordSheetService {
	private final CloudFrontService cloudFrontService;

	public PreSignedUrlVO getExamRecordSheetPreSignedUrl() {
		return cloudFrontService.createPreSignedPutUrl(EXAM_SHEET_FOLDER_NAME);
	}
}
