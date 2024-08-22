package com.nonsoolmate.nonsoolmateServer.domain.coupon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.request.IssueCouponRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response.GetCouponsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.service.CouponService;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.RegisterCouponRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.global.security.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController implements CouponApi {
	private final CouponService couponService;

	@Override
	@PostMapping("/issue")
	public ResponseEntity<Void> issueCoupon(@RequestBody @Valid IssueCouponRequestDTO requestDTO) {

		couponService.issueCoupon(requestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@Override
	@GetMapping
	public ResponseEntity<GetCouponsResponseDTO> getCoupons(@AuthUser Member member) {
		GetCouponsResponseDTO responseDTO = couponService.getCoupons(member);

		return ResponseEntity.ok().body(responseDTO);
	}

	@Override
	@PostMapping
	public ResponseEntity<Void> registerCoupon(
		@RequestBody @Valid RegisterCouponRequestDTO requestDTO,
		@AuthUser Member member) {
		couponService.registerCoupon(requestDTO, member);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
