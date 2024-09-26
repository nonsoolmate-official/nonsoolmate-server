package com.nonsoolmate.payment.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.payment.controller.dto.request.CreateCardRequestDTO;
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
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity<CardResponseDTO> registerCard(
			@RequestBody @Valid CreateCardRequestDTO createCardRequestDTO) {
		CardResponseDTO response = billingService.registerCard(createCardRequestDTO);
		return ResponseEntity.ok(response);
	}
}
