package com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.examRecord.entity.ExamRecord;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.repository.ExamRecordRepository;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeExamResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeExamsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeUpdateResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.entity.SelectCollege;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.repository.SelectCollegeRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.College;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.CollegeRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.ExamRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.repository.UniversityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectUniversityService {
	private static final String BEFORE_EXAM = "시험 응시 전";
	private final UniversityRepository universityRepository;
	private final ExamRepository examRepository;
	private final ExamRecordRepository examRecordRepository;
	private final SelectCollegeRepository selectCollegeRepository;
	private final CollegeRepository collegeRepository;

	public List<SelectCollegeResponseDTO> getSelectColleges(Member member) {
		List<College> colleges = collegeRepository.findAllByOrderByUniversityNameAscCollegeNameAsc();

		Set<Long> selectedUniversityIds = selectCollegeRepository.findUniversityIdsByMember(member);

		return colleges.stream()
			.map(college -> SelectCollegeResponseDTO.of(
				college.getCollegeId(),
				college.getUniversity().getUniversityName(),
				college.getCollegeName(),
				selectedUniversityIds.contains(college.getCollegeId())
			))
			.collect(Collectors.toList());
	}

	public List<SelectCollegeExamsResponseDTO> getSelectCollegeExams(final Member member) {
		final List<SelectCollege> selectColleges = selectCollegeRepository.findAllByMemberOrderByUniversityNameAscCollegeNameAsc(
			member);

		return selectColleges.stream()
			.map(selectCollege -> getSelectCollegeExamsResponseDTO(selectCollege, member))
			.collect(Collectors.toList());
	}

	private SelectCollegeExamsResponseDTO getSelectCollegeExamsResponseDTO(
		final SelectCollege selectCollege,
		final Member member) {

		final List<Exam> exams = examRepository.findAllByCollegeOrderByExamYearDesc(
			selectCollege.getCollege());

		final List<SelectCollegeExamResponseDTO> selectCollegeExamResponseDTOS = getSelectCollegeExamResponseDTOS(
			exams, member);

		return SelectCollegeExamsResponseDTO.of(selectCollege.getCollege().getCollegeId(),
			selectCollege.getCollege().getUniversity().getUniversityName(),
			selectCollege.getCollege().getCollegeName(), selectCollegeExamResponseDTOS);
	}

	private List<SelectCollegeExamResponseDTO> getSelectCollegeExamResponseDTOS(
		final List<Exam> exams, final Member member) {
		ExamRecord examRecord;
		List<SelectCollegeExamResponseDTO> selectCollegeExamResponseDTOS = new ArrayList<>();
		for (Exam exam : exams) {
			examRecord = examRecordRepository.findByExamAndMember(exam, member)
				.orElse(null);
			String status =
				examRecord == null ? BEFORE_EXAM : examRecord.getExamResultStatus().getStatus();
			selectCollegeExamResponseDTOS.add(
				SelectCollegeExamResponseDTO.of(exam.getExamId(),
					exam.getExamListName(), exam.getExamTimeLimit(),
					status));
		}
		return selectCollegeExamResponseDTOS;
	}

	@Transactional
	public SelectCollegeUpdateResponseDTO patchSelectColleges(
		Member member,
		List<Long> selectedCollegeIds) {

		selectCollegeRepository.deleteAllByMemberId(member.getMemberId());

		List<College> colleges = collegeRepository.findAllByCollegeIdIn(selectedCollegeIds);
		List<SelectCollege> selectColleges = colleges.stream()
			.map(university -> new SelectCollege(member, university))
			.toList();

		selectCollegeRepository.saveAll(selectColleges);

		return SelectCollegeUpdateResponseDTO.of(true);
	}
}
