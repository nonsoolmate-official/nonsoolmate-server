package com.nonsoolmate.nonsoolmateServer.domain.coupon.service;

import static com.nonsoolmate.nonsoolmateServer.domain.coupon.exception.CouponExceptionType.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.nonsoolmateServer.domain.common.random.CustomRandom;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.request.IssueCouponRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response.GetCouponResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.controller.dto.response.GetCouponsResponseDTO;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.entity.Coupon;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.exception.CouponException;
import com.nonsoolmate.nonsoolmateServer.domain.coupon.repository.CouponRepository;
import com.nonsoolmate.nonsoolmateServer.domain.couponMember.entity.CouponMember;
import com.nonsoolmate.nonsoolmateServer.domain.couponMember.repository.CouponMemberRepository;
import com.nonsoolmate.nonsoolmateServer.domain.examRecord.controller.dto.request.RegisterCouponRequestDTO;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponService {
	private final CouponRepository couponRepository;
	private final CouponMemberRepository couponMemberRepository;

	private final CustomRandom customRandom;

	@Value("${coupon.secret}")
	private String couponSecretValue;

	@Transactional
	public void issueCoupon(IssueCouponRequestDTO requestDTO) {
		boolean validRequest = requestDTO.getSecretValue().equals(couponSecretValue);
		if (!validRequest) {
			throw new CouponException(INVALID_COUPON_ISSUE);
		}

		String couponNumber = requestDTO.getCouponNumber();

		if (couponNumber == null) {
			couponNumber = customRandom.generateRandomValue();
		}

		Coupon coupon = requestDTO.toEntity(couponNumber);
		couponRepository.save(coupon);
	}

	public GetCouponsResponseDTO getCoupons(Member member) {
		List<GetCouponResponseDTO> responseDTOs = couponMemberRepository.findAllByMemberIdWithCoupon(
			member.getMemberId());

		return GetCouponsResponseDTO.of(responseDTOs);
	}

	@Transactional
	public void registerCoupon(RegisterCouponRequestDTO requestDTO, Member member) {
		Coupon coupon = couponRepository.findByCouponNumberOrThrow(requestDTO.couponNumber());

		Optional<CouponMember> foundCouponMember = couponMemberRepository.findByCouponId(coupon.getCouponId());

		if(foundCouponMember.isPresent()){
			throw new CouponException(INVALID_COUPON_REGISTER);
		}

		CouponMember couponMember = createCouponMember(member, coupon);
		couponMemberRepository.save(couponMember);
	}

	private CouponMember createCouponMember(Member member, Coupon coupon) {
		return CouponMember.builder()
			.couponId(coupon.getCouponId())
			.memberId(member.getMemberId())
			.isUsed(false)
			.build();
	}
}
