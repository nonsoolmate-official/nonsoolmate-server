package com.nonsoolmate.member.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.controller.dto.request.ProfileRequestDTO;
import com.nonsoolmate.member.controller.dto.response.NameResponseDTO;
import com.nonsoolmate.member.controller.dto.response.ProfileResponseDTO;
import com.nonsoolmate.member.controller.dto.response.TeacherResponseDTO;
import com.nonsoolmate.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "my", description = "멤버와 관련된 API")
public interface MemberApi {
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "이름 조회에 성공하였습니다.")})
  @Operation(summary = "마이페이지: 이름", description = "내 이름을 조회합니다.")
  ResponseEntity<SuccessResponse<NameResponseDTO>> getName(
      @Parameter(hidden = true) @AuthMember String memberId);

  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "프로필 조회에 성공하였습니다.")})
  @Operation(summary = "내 프로필 조회", description = "내 프로필을 조회합니다.")
  ResponseEntity<SuccessResponse<ProfileResponseDTO>> getProfile(
      @Parameter(hidden = true) @AuthMember String memberId);

  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "프로필 수정에 성공하였습니다.")})
  @Operation(summary = "내 프로필 수정", description = "내 프로필을 수정합니다.")
  ResponseEntity<Void> editProfile(
      @RequestBody @Valid ProfileRequestDTO profileRequestDTO,
      @Parameter(hidden = true) @AuthMember String memberId);

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "첨삭 선생님이 아직 매칭되지 않았습니다."),
        @ApiResponse(responseCode = "200", description = "첨삭 선생님 조회에 성공했습니다."),
        @ApiResponse(
            responseCode = "204",
            description = "매칭된 첨삭 선생님이 존재하지 않습니다.",
            content = @Content)
      })
  @Operation(
      summary = "내 첨삭 선생님 조회",
      description =
          """
        내 첨삭 선생님을 조회합니다. \n
        1. 결제 하지 않은 경우 : 204 응답 \n
        2. 결제는 했으나 첨삭 선생님이 매칭되지 않은 경우 : isMatched = false \n
        3. 결제를 했고, 첨삭 선생님이 매칭된 경우 : isMatched = true \n
        """)
  ResponseEntity<TeacherResponseDTO> getMyTeacher(
      @Parameter(hidden = true) @AuthMember String memberId);
}
