package com.nonsoolmate.targetUniversity.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.targetUniversity.controller.dto.request.TargetUniversityRequestDTO;
import com.nonsoolmate.targetUniversity.controller.dto.response.TargetUniversityResponseDTO;
import com.nonsoolmate.targetUniversity.entity.TargetUniversity;
import com.nonsoolmate.targetUniversity.repository.TargetUniversityRepository;
import com.nonsoolmate.university.entity.University;
import com.nonsoolmate.university.repository.UniversityRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TargetUniversityService {
  private final UniversityRepository universityRepository;
  private final TargetUniversityRepository targetUniversityRepository;
  private final MemberRepository memberRepository;

  public List<TargetUniversityResponseDTO> getTargetUniversities() {
    List<University> universities = universityRepository.findAll();

    return universities.stream().map(TargetUniversityResponseDTO::of).toList();
  }

  @Transactional
  public void patchTargetUniversity(
      final List<TargetUniversityRequestDTO> request, final String memberId) {
    Member member = memberRepository.findByMemberIdOrThrow(memberId);
    targetUniversityRepository.deleteAllByMember(member);
    List<Long> universityIds =
        request.stream().map(TargetUniversityRequestDTO::universityId).toList();
    List<University> universities = universityRepository.findAllByUniversityIdIn(universityIds);
    List<TargetUniversity> targetUniversities =
        universities.stream().map(university -> new TargetUniversity(university, member)).toList();
    targetUniversityRepository.saveAll(targetUniversities);
  }
}
