package com.nonsoolmate.payment.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.payment.controller.dto.request.CreateOrUpdateCardRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.CardResponseDTO;
import com.nonsoolmate.payment.service.BillingService;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController implements CardApi {
  private final BillingService billingService;

  @GetMapping
  public ResponseEntity<CardResponseDTO> getCard(@AuthMember final String memberId) {
    CardResponseDTO response = billingService.getCard(memberId);
    boolean hasCard = response != null;
    if (hasCard) {
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/register")
  public ResponseEntity<CardResponseDTO> registerCard(
      @RequestBody @Valid CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO,
      @AuthMember final String memberId) {
    CardResponseDTO response = billingService.registerCard(createOrUpdateCardRequestDTO, memberId);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/update")
  public ResponseEntity<CardResponseDTO> updateCard(
      @RequestBody @Valid CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO,
      @AuthMember final String memberId) {
    CardResponseDTO response = billingService.updateCard(createOrUpdateCardRequestDTO, memberId);
    return ResponseEntity.ok(response);
  }
}
