package com.nonsoolmate.member.controller.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nonsoolmate.member.entity.enums.MembershipStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "MembershipStatusResponseDTO", description = "멤버십 정보 응답 DTO")
public record MembershipStatusResponseDTO(
    @Schema(
            description = "멤버십 상태(IN_PROGRESS: 진행중일 때 / TERMINATED: 해지했을 때)",
            example = "IN_PROGRESS")
        MembershipStatus status,
    @Schema(description = "멤버십 이름", example = "베이직 플랜") String membershipName,
    @Schema(description = "멤버십 이용 기간", example = "2024.10.01 ~ 2024.10.28")
        String membershipUsingDate) {
  public static MembershipStatusResponseDTO of(
      final MembershipStatus status,
      final String membershipName,
      final LocalDateTime startDate,
      final LocalDateTime endDate) {
    String membershipUsingDate = dateFormating(startDate, endDate);
    return new MembershipStatusResponseDTO(status, membershipName, membershipUsingDate);
  }

  private static String dateFormating(LocalDateTime startDate, LocalDateTime endDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    return startDate.format(formatter) + " ~ " + endDate.format(formatter);
  }
}
