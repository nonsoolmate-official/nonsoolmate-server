package com.nonsoolmate.payment.controller;

import java.net.URI;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.service.MemberService;
import com.nonsoolmate.payment.controller.dto.request.CreatePaymentRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.CustomerInfoDTO;
import com.nonsoolmate.payment.controller.dto.response.PaymentResponseDTO;
import com.nonsoolmate.payment.service.PaymentService;

@RestController
@Slf4j
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {
	private static final String PAYMENT_URL = "/payment/";
	private final MemberService memberService;
	private final PaymentService paymentService;

	@GetMapping("/customer/info")
	public ResponseEntity<CustomerInfoDTO> getCustomerInfo(@AuthMember final String memberId) {
		return ResponseEntity.ok().body(memberService.getCustomerInfo(memberId));
	}

	@PostMapping("/membership")
	public ResponseEntity<PaymentResponseDTO> createMembershipPayment(
			@Valid @RequestBody final CreatePaymentRequestDTO paymentRequestDTO,
			@AuthMember final String memberId) {
		PaymentResponseDTO response = paymentService.createBillingPayment(paymentRequestDTO, memberId);
		URI uri = URI.create(PAYMENT_URL + response.paymentId());
		return ResponseEntity.created(uri).body(response);
	}
}
