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
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityExamResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityExamsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.controller.dto.response.SelectUniversityUpdateResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.selectUniversity.repository.SelectCollegeRepository;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.College;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.Exam;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;
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

	public List<SelectUniversityExamsResponseDTO> getSelectUniversityExams(final Member member) {
		List<SelectUniversityExamsResponseDTO> selectUniversityExamsResponseDTOS = new ArrayList<>();
		final List<SelectUniversity> selectUniversities = selectCollegeRepository.findAllByMemberOrderByUniversityNameAscCollegeNameAsc(
			member);

		for (SelectUniversity selectUniversity : selectUniversities) {
			selectUniversityExamsResponseDTOS.add(getSelectUniversityExamsResponseDTO(selectUniversity, member));
		}

		return selectUniversityExamsResponseDTOS;
	}

	private SelectUniversityExamsResponseDTO getSelectUniversityExamsResponseDTO(
		final SelectUniversity selectUniversity,
		final Member member) {

		final List<Exam> exams = examRepository.findAllByCollegeOrderByExamYearDesc(
			selectUniversity.getCollege());

		final List<SelectUniversityExamResponseDTO> selectUniversityExamResponseDTOS = getSelectUniversityExamResponseDTOS(
			exams, member);

		return SelectUniversityExamsResponseDTO.of(selectUniversity.getCollege().getCollegeId(),
			selectUniversity.getCollege().getUniversity().getUniversityName(),
			selectUniversity.getCollege().getCollegeName(), selectUniversityExamResponseDTOS);
	}

	private List<SelectUniversityExamResponseDTO> getSelectUniversityExamResponseDTOS(
		final List<Exam> exams, final Member member) {
		ExamRecord examRecord;
		List<SelectUniversityExamResponseDTO> selectUniversityExamResponseDTOS = new ArrayList<>();
		for (Exam exam : exams) {
			examRecord = examRecordRepository.findByExamAndMember(exam, member)
				.orElse(null);
			String status =
				examRecord == null ? BEFORE_EXAM : examRecord.getExamResultStatus().getStatus();
			selectUniversityExamResponseDTOS.add(
				SelectUniversityExamResponseDTO.of(exam.getExamId(),
					exam.getExamListName(), exam.getExamTimeLimit(),
					status));
		}
		return selectUniversityExamResponseDTOS;
	}

	@Transactional
	public SelectUniversityUpdateResponseDTO patchSelectUniversities(
		Member member,
		List<Long> selectedUniversityIds) {

		selectUniversityRepository.deleteAllByMemberId(member.getMemberId());

		List<University> universities = universityRepository.findAllByUniversityIdIn(selectedUniversityIds);
		List<SelectUniversity> selectUniversities = universities.stream()
			.map(university -> new SelectUniversity(member, university))
			.toList();

		selectUniversityRepository.saveAll(selectUniversities);

		return SelectUniversityUpdateResponseDTO.of(true);
	}
}
