package com.nonsoolmate.member.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.controller.dto.request.MembershipStatusRequestDTO;
import com.nonsoolmate.member.controller.dto.response.MembershipAndTicketResponseDTO;
import com.nonsoolmate.member.controller.dto.response.MembershipStatusResponseDTO;
import com.nonsoolmate.member.controller.dto.response.PaymentInfoResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "membership", description = "멤버십과 관련된 API")
public interface MembershipApi {
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "내 멤버십과 첨삭권 개수를 조회합니다")})
  @Operation(summary = "모달: 내 멤버십과 첨삭권 개수 확인", description = "이름, 멤버십, 첨삭권 개수를 조회합니다")
  ResponseEntity<MembershipAndTicketResponseDTO> getMembershipAndTicket(
      @Parameter(hidden = true) @AuthMember String memberId);

  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "다음달 결제 정보를 조회합니다.")})
  @Operation(summary = "다음달 결제 정보", description = "다음달 결제 정보를 조회합니다.")
  ResponseEntity<PaymentInfoResponseDTO> getNextPaymentInfo(
      @Parameter(hidden = true) @AuthMember String memberId);

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "멤버십 상태 변경에 성공한 경우"),
        @ApiResponse(responseCode = "404", description = "멤버십 구매를 하지 않았는데 상태 변경을 요청하는 경우")
      })
  @Operation(summary = "멤버십 상태 변경", description = "멤버십 상태를 변경합니다.")
  ResponseEntity<MembershipStatusResponseDTO> changeMembershipStatus(
      @Parameter(hidden = true) @AuthMember String memberId,
      @Parameter(description = "멤버십 변경 요청 dto", required = true) @Valid
          MembershipStatusRequestDTO request);

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "멤버십 조회에 성공한 경우"),
        @ApiResponse(responseCode = "204", description = "멤버십 구매를 하지 않았는데 멤버십 조회를 요청하는 경우")
      })
  @Operation(summary = "멤버십 조회", description = "멤버십 상태를 조회합니다")
  ResponseEntity<MembershipStatusResponseDTO> getMembership(
      @Parameter(hidden = true) @AuthMember String memberId);
}
