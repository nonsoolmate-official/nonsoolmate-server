package com.nonsoolmate.member.controller;

import java.util.Optional;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.controller.dto.request.MembershipStatusRequestDTO;
import com.nonsoolmate.member.controller.dto.response.MembershipAndTicketResponseDTO;
import com.nonsoolmate.member.controller.dto.response.MembershipStatusResponseDTO;
import com.nonsoolmate.member.controller.dto.response.PaymentInfoResponseDTO;
import com.nonsoolmate.member.service.MembershipService;

@RestController
@RequestMapping("/membership")
@RequiredArgsConstructor
public class MembershipController implements MembershipApi {

  private final MembershipService membershipService;

  @Override
  @GetMapping("/ticket")
  public ResponseEntity<MembershipAndTicketResponseDTO> getMembershipAndTicket(
      @AuthMember final String memberId) {
    return ResponseEntity.ok().body(membershipService.getMembershipAndTicket(memberId));
  }

  @Override
  @GetMapping("/payment")
  public ResponseEntity<PaymentInfoResponseDTO> getNextPaymentInfo(
      @AuthMember final String memberId) {
    Optional<PaymentInfoResponseDTO> nextPaymentInfo =
        membershipService.getNextPaymentInfo(memberId);

    if (nextPaymentInfo.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok().body(nextPaymentInfo.get());
  }

  @Override
  @PatchMapping("/status")
  public ResponseEntity<MembershipStatusResponseDTO> changeMembershipStatus(
      @AuthMember final String memberId,
      @Valid @RequestBody final MembershipStatusRequestDTO request) {
    MembershipStatusResponseDTO response =
        membershipService.changeMembershipStatus(memberId, request);
    return ResponseEntity.ok().body(response);
  }
}
