package com.nonsoolmate.member.service;

import static com.nonsoolmate.aws.FolderName.UNIVERSITY_IMAGE_FOLDER_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.aws.service.CloudFrontService;
import com.nonsoolmate.matching.entity.Matching;
import com.nonsoolmate.matching.repository.MatchingRepository;
import com.nonsoolmate.member.controller.dto.request.ProfileRequestDTO;
import com.nonsoolmate.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.member.controller.dto.response.TeacherResponseDTO;
import com.nonsoolmate.member.controller.dto.response.TeacherUniversityDTO;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.member.repository.MembershipRepository;
import com.nonsoolmate.payment.controller.dto.response.CustomerInfoDTO;
import com.nonsoolmate.tag.entity.Tag;
import com.nonsoolmate.tag.repository.TagRepository;
import com.nonsoolmate.teacher.entity.Teacher;
import com.nonsoolmate.teacher.entity.TeacherUniversity;
import com.nonsoolmate.teacher.repository.TeacherUniversityRepository;
import com.nonsoolmate.university.entity.University;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;
  private final MatchingRepository matchingRepository;
  private final TagRepository tagRepository;
  private final TeacherUniversityRepository teacherUniversityRepository;
  private final CloudFrontService cloudFrontService;
  private final MembershipRepository membershipRepository;

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
      return Optional.of(TeacherResponseDTO.of(false, null, null, null, null));
    }

    Teacher teacher = matching.getTeacher();
    Member member = memberRepository.findByMemberIdOrThrow(memberId);
    Optional<MembershipType> membershipType =
        membershipRepository.findMembershipTypeByMember(member);
    String qnaLink =
        membershipType.isPresent() && membershipType.get().equals(MembershipType.PREMIUM)
            ? teacher.getQnaLink()
            : null;
    List<TeacherUniversity> teacherUniversities =
        teacherUniversityRepository.findAllByTeacher(teacher);
    List<TeacherUniversityDTO> teacherUniversityDTOs = new ArrayList<>();

    for (TeacherUniversity teacherUniversity : teacherUniversities) {
      University university = teacherUniversity.getUniversity();
      teacherUniversityDTOs.add(getTeacherUniversityDTO(university));
    }

    List<Tag> tags = tagRepository.findAllByTeacherId(teacher.getTeacherId());

    return Optional.of(TeacherResponseDTO.of(true, teacher, qnaLink, teacherUniversityDTOs, tags));
  }

  private TeacherUniversityDTO getTeacherUniversityDTO(final University university) {
    String universityName = university.getUniversityName();
    String universityImageName = university.getUniversityImageUrl();
    String universityImageUrl =
        cloudFrontService.createPreSignedGetUrl(UNIVERSITY_IMAGE_FOLDER_NAME, universityImageName);
    return TeacherUniversityDTO.of(universityName, universityImageUrl);
  }
}
