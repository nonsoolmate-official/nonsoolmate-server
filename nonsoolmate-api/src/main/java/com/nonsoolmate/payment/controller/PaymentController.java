package com.nonsoolmate.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.global.security.AuthMember;
import com.nonsoolmate.member.service.MemberService;
import com.nonsoolmate.payment.controller.dto.response.CustomerInfoDTO;

@RestController
@Slf4j
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentApi {

	private final MemberService memberService;

	@GetMapping("/customer/info")
	public ResponseEntity<CustomerInfoDTO> getCustomerInfo(@AuthMember String memberId) {
		return ResponseEntity.ok().body(memberService.getCustomerInfo(memberId));
	}
}
