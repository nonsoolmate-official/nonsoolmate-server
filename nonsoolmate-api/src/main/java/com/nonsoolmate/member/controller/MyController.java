package com.nonsoolmate.member.controller;

import static com.nonsoolmate.exception.member.MemberSuccessType.*;

import java.util.Optional;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nonsoolmate.exception.member.MemberSuccessType;
import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.controller.dto.request.ProfileRequestDTO;
import com.nonsoolmate.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.member.controller.dto.response.TeacherResponseDTO;
import com.nonsoolmate.member.service.MemberService;
import com.nonsoolmate.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController implements MemberApi {
  private final MemberService memberService;

  @Override
  @GetMapping("/name")
  public ResponseEntity<SuccessResponse<NameResponseDTO>> getName(
      @AuthMember final String memberId) {
    return ResponseEntity.ok()
        .body(
            SuccessResponse.of(
                MemberSuccessType.GET_MEMBER_NAME_SUCCESS, memberService.getNickname(memberId)));
  }

  @Override
  @GetMapping("/profile")
  public ResponseEntity<SuccessResponse<ProfileResponseDTO>> getProfile(
      @AuthMember final String memberId) {
    ProfileResponseDTO responseDTO = memberService.getProfile(memberId);

    return ResponseEntity.ok().body(SuccessResponse.of(GET_MEMBER_PROFILE_SUCCESS, responseDTO));
  }

  @Override
  @PutMapping("/profile")
  public ResponseEntity<Void> editProfile(
      @RequestBody @Valid ProfileRequestDTO profileRequestDTO, @AuthMember String memberId) {
    memberService.editProfile(profileRequestDTO, memberId);

    return ResponseEntity.ok().build();
  }

  @Override
  @GetMapping("/teacher")
  public ResponseEntity<TeacherResponseDTO> getMyTeacher(
      @Parameter(hidden = true) @AuthMember String memberId) {

    Optional<TeacherResponseDTO> teacherResponseDTO = memberService.getMyTeacher(memberId);

    boolean noMatchedTeacher = teacherResponseDTO.isEmpty();
    if (noMatchedTeacher) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok().body(teacherResponseDTO.get());
  }
}
