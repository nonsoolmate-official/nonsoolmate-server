package com.nonsoolmate.coupon.service;

import static com.nonsoolmate.exception.coupon.CouponExceptionType.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nonsoolmate.common.random.CustomRandom;
import com.nonsoolmate.coupon.controller.dto.request.IssueCouponRequestDTO;
import com.nonsoolmate.coupon.controller.dto.response.GetCouponsResponseDTO;
import com.nonsoolmate.coupon.entity.Coupon;
import com.nonsoolmate.coupon.repository.CouponRepository;
import com.nonsoolmate.couponMember.entity.CouponMember;
import com.nonsoolmate.couponMember.repository.CouponMemberRepository;
import com.nonsoolmate.couponMember.repository.dto.CouponResponseDTO;
import com.nonsoolmate.examRecord.controller.dto.request.RegisterCouponRequestDTO;
import com.nonsoolmate.exception.coupon.CouponException;

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
		List<CouponResponseDTO> responseDTOs =
				couponMemberRepository.findAllByMemberIdWithCoupon(memberId);

		return GetCouponsResponseDTO.of(responseDTOs);
	}

	@Transactional
	public void registerCoupon(RegisterCouponRequestDTO requestDTO, String memberId) {
		Coupon coupon = couponRepository.findByCouponNumberOrThrow(requestDTO.couponNumber());

		Optional<CouponMember> foundCouponMember =
				couponMemberRepository.findByCouponId(coupon.getCouponId());

		if (foundCouponMember.isPresent()) {
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
