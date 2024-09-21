package com.nonsoolmate.payment.service;

import static com.nonsoolmate.exception.payment.BillingExceptionType.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nonsoolmate.exception.payment.BillingException;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.PlatformType;
import com.nonsoolmate.member.entity.enums.Role;
import com.nonsoolmate.payment.controller.dto.response.CardResponseDTO;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;

@SpringBootTest
@ActiveProfiles("test")
class BillingServiceTest {
	@Mock BillingRepository billingRepository;

	@InjectMocks BillingService billingService;

	private static final String MEMBER_ID = "testMemberId";
	private static final String NOT_REGISTERED_CARD_EXCEPTION_MESSAGE = "사용자가 카드를 등록하지 않았습니다";

	@Test
	@DisplayName("사용자가 등록한 카드를 확인하는 경우")
	void getCardTestWhenMemberHasRegisteredCard() {
		// given
		Member expectedMember =
				Member.builder()
						.email("testEmail")
						.name("testName")
						.platformType(PlatformType.NAVER)
						.platformId("testPlatformId")
						.role(Role.USER)
						.birthYear("test")
						.build();
		Billing expectedBilling =
				Billing.builder()
						.billingKey("testBillingKey")
						.cardCompany("testCardCompany")
						.cardNumber("testCardNumber")
						.customer(expectedMember)
						.build();
		CardResponseDTO mockResponse =
				CardResponseDTO.of(
						expectedBilling.getBillingId(),
						expectedBilling.getCardCompany(),
						expectedBilling.getCardNumber());

		given(billingRepository.findByCustomerIdOrThrow(anyString())).willReturn(expectedBilling);

		// when
		CardResponseDTO response = billingService.getCard(MEMBER_ID);

		// then
		Assertions.assertThat(response).isEqualTo(mockResponse);
	}

	@Test
	@DisplayName("사용자가 카드를 등록하지 않은 경우")
	void getCardTestWhenMemberHasNotRegisteredCard() {
		// given
		given(billingRepository.findByCustomerIdOrThrow(anyString()))
				.willThrow(new BillingException(NOT_FOUND_BILLING));

		// when, then
		Assertions.assertThatThrownBy(() -> billingService.getCard(MEMBER_ID))
				.isInstanceOf(BillingException.class)
				.hasMessage(NOT_REGISTERED_CARD_EXCEPTION_MESSAGE);
	}
}
