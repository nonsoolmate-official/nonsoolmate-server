package com.nonsoolmate.member.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.matching.entity.Matching;
import com.nonsoolmate.matching.repository.MatchingRepository;
import com.nonsoolmate.member.controller.dto.request.ProfileRequestDTO;
import com.nonsoolmate.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.member.controller.dto.response.TeacherResponseDTO;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.payment.controller.dto.response.CustomerInfoDTO;
import com.nonsoolmate.tag.entity.Tag;
import com.nonsoolmate.tag.repository.TagRepository;
import com.nonsoolmate.teacher.entity.Teacher;
import com.nonsoolmate.teacher.entity.TeacherUniversity;
import com.nonsoolmate.teacher.repository.TeacherUniversityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final MatchingRepository matchingRepository;
	private final TagRepository tagRepository;
	private final TeacherUniversityRepository teacherUniversityRepository;

	public NameResponseDTO getNickname(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);
		return NameResponseDTO.of(member.getName());
	}

	public ProfileResponseDTO getProfile(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		return ProfileResponseDTO.of(
				member.getName(),
				member.getGender(),
				member.getBirthYear(),
				member.getEmail(),
				member.getPhoneNumber());
	}

	public CustomerInfoDTO getCustomerInfo(final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		return CustomerInfoDTO.of(member.getMemberId(), member.getName(), member.getEmail());
	}

	@Transactional
	public void editProfile(final ProfileRequestDTO profileRequestDTO, final String memberId) {
		Member member = memberRepository.findByMemberIdOrThrow(memberId);

		member.updateMemberProfile(
				profileRequestDTO.name(),
				profileRequestDTO.gender(),
				profileRequestDTO.birthYear(),
				profileRequestDTO.email(),
				profileRequestDTO.phoneNumber());
	}

	public Optional<TeacherResponseDTO> getMyTeacher(final String memberId) {

		Optional<Matching> foundMatching =
				matchingRepository.findByMemberIdWithTeacherAndMember(memberId);

		if (foundMatching.isEmpty()) {
			return Optional.empty();
		}

		Matching matching = foundMatching.get();

		if (matching.getTeacher() == null) {
			return Optional.of(TeacherResponseDTO.of(false, null, null, null));
		}

		Teacher teacher = matching.getTeacher();
		List<TeacherUniversity> teacherUniversities =
				teacherUniversityRepository.findAllByTeacher(teacher);
		List<Tag> tags = tagRepository.findAllByTeacherId(teacher.getTeacherId());

		return Optional.of(TeacherResponseDTO.of(true, teacher, teacherUniversities, tags));
	}
}
