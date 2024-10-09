package com.nonsoolmate.selectCollege.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.examRecord.entity.ExamRecordGroups;
import com.nonsoolmate.examRecord.repository.ExamRecordRepository;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.selectCollege.controller.dto.response.SelectCollegeExamResponseDTO;
import com.nonsoolmate.selectCollege.controller.dto.response.SelectCollegeExamsResponseDTO;
import com.nonsoolmate.selectCollege.controller.dto.response.SelectCollegeResponseDTO;
import com.nonsoolmate.selectCollege.controller.dto.response.SelectCollegeUpdateResponseDTO;
import com.nonsoolmate.selectCollege.entity.SelectCollege;
import com.nonsoolmate.selectCollege.repository.SelectCollegeRepository;
import com.nonsoolmate.university.entity.College;
import com.nonsoolmate.university.entity.Exam;
import com.nonsoolmate.university.repository.CollegeRepository;
import com.nonsoolmate.university.repository.ExamRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SelectCollegeService {
	private final ExamRepository examRepository;
	private final ExamRecordRepository examRecordRepository;
	private final SelectCollegeRepository selectCollegeRepository;
	private final CollegeRepository collegeRepository;
	private final MemberRepository memberRepository;

	public List<SelectCollegeResponseDTO> getSelectColleges(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		List<College> colleges = collegeRepository.findAllOrderByUniversityNameAscCollegeNameAsc();

		List<SelectCollege> selectedUniversities = selectCollegeRepository.findAllByMember(member);
		List<Long> selectedUniversityIds =
				selectedUniversities.stream().map(s -> s.getCollege().getCollegeId()).toList();

		return colleges.stream()
				.map(
						college ->
								SelectCollegeResponseDTO.of(
										college.getCollegeId(),
										college.getUniversity().getUniversityName(),
										college.getCollegeName(),
										selectedUniversityIds.contains(college.getCollegeId())))
				.toList();
	}

	public List<SelectCollegeExamsResponseDTO> getSelectCollegeExams(final String memberId) {
		final List<SelectCollege> sortedSelectColleges =
				selectCollegeRepository.findAllByMemberOrderByUniversityNameAscCollegeNameAsc(memberId);
		final List<Long> sortedCollegeIds = getSortedCollegeIds(sortedSelectColleges);
		final List<Exam> exams =
				examRepository.findAllByCollegeIdInOrderByExamYearDesc(sortedCollegeIds);
		final List<Long> examIds = exams.stream().map(Exam::getExamId).toList();

		ExamRecordGroups examRecordGroups =
				new ExamRecordGroups(examRecordRepository.findAllByExamIdInAndMemberId(examIds, memberId));

		final Map<Long, List<SelectCollegeExamResponseDTO>> examResponseMap =
				getSelectCollegeExamResponseMap(exams, examRecordGroups);

		return getSelectCollegeExamsResponseDTOS(sortedSelectColleges, examResponseMap);
	}

	private List<Long> getSortedCollegeIds(List<SelectCollege> sortedSelectColleges) {
		return sortedSelectColleges.stream()
				.map(selectCollege -> selectCollege.getCollege().getCollegeId())
				.toList();
	}

	/**
	 * @note: key = collegeId
	 */
	private Map<Long, List<SelectCollegeExamResponseDTO>> getSelectCollegeExamResponseMap(
			final List<Exam> exams, final ExamRecordGroups examRecordGroups) {
		return exams.stream()
				.collect(
						Collectors.groupingBy(
								exam -> exam.getCollege().getCollegeId(),
								Collectors.mapping(
										exam ->
												SelectCollegeExamResponseDTO.of(
														exam,
														examRecordGroups.getExamResultStatus(exam.getExamId()).getStatus()),
										Collectors.toList())));
	}

	private List<SelectCollegeExamsResponseDTO> getSelectCollegeExamsResponseDTOS(
			List<SelectCollege> sortedSelectColleges,
			Map<Long, List<SelectCollegeExamResponseDTO>> examResponseMap) {
		return sortedSelectColleges.stream()
				.map(
						selectCollege ->
								SelectCollegeExamsResponseDTO.of(
										selectCollege.getCollege(),
										examResponseMap.get(selectCollege.getCollege().getCollegeId())))
				.toList();
	}

	@Transactional
	public SelectCollegeUpdateResponseDTO patchSelectColleges(
			final String memberId, List<Long> selectedCollegeIds) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		selectCollegeRepository.deleteAllByMemberId(memberId);

		List<College> colleges = collegeRepository.findAllByCollegeIdIn(selectedCollegeIds);
		List<SelectCollege> selectColleges =
				colleges.stream().map(college -> new SelectCollege(member, college)).toList();

		selectCollegeRepository.saveAll(selectColleges);

		return SelectCollegeUpdateResponseDTO.of(true);
	}
}
