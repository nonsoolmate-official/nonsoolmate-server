package com.nonsoolmate.domain.coupon.service;

import static com.nonsoolmate.domain.coupon.exception.CouponExceptionType.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.domain.common.random.CustomRandom;
import com.nonsoolmate.domain.coupon.controller.dto.request.IssueCouponRequestDTO;
import com.nonsoolmate.domain.coupon.controller.dto.response.GetCouponResponseDTO;
import com.nonsoolmate.domain.coupon.controller.dto.response.GetCouponsResponseDTO;
import com.nonsoolmate.domain.coupon.entity.Coupon;
import com.nonsoolmate.domain.coupon.exception.CouponException;
import com.nonsoolmate.domain.coupon.repository.CouponRepository;
import com.nonsoolmate.domain.couponMember.entity.CouponMember;
import com.nonsoolmate.domain.couponMember.repository.CouponMemberRepository;
import com.nonsoolmate.domain.examRecord.controller.dto.request.RegisterCouponRequestDTO;

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

	public GetCouponsResponseDTO getCoupons(String memberId) {
		List<GetCouponResponseDTO> responseDTOs = couponMemberRepository.findAllByMemberIdWithCoupon(memberId);

		return GetCouponsResponseDTO.of(responseDTOs);
	}

	@Transactional
	public void registerCoupon(RegisterCouponRequestDTO requestDTO, String memberId) {
		Coupon coupon = couponRepository.findByCouponNumberOrThrow(requestDTO.couponNumber());

		Optional<CouponMember> foundCouponMember = couponMemberRepository.findByCouponId(coupon.getCouponId());

		if(foundCouponMember.isPresent()){
			throw new CouponException(INVALID_COUPON_REGISTER);
		}

		CouponMember couponMember = createCouponMember(memberId, coupon);
		couponMemberRepository.save(couponMember);
	}

	private CouponMember createCouponMember(String memberId, Coupon coupon) {
		return CouponMember.builder()
			.couponId(coupon.getCouponId())
			.memberId(memberId)
			.isUsed(false)
			.build();
	}
}
