package com.nonsoolmate.payment.service;

import static com.nonsoolmate.exception.member.MemberExceptionType.*;
import static com.nonsoolmate.exception.payment.BillingExceptionType.*;
import static org.mockito.BDDMockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.nonsoolmate.exception.auth.AuthException;
import com.nonsoolmate.exception.payment.BillingException;
import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.member.entity.enums.PlatformType;
import com.nonsoolmate.member.entity.enums.Role;
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.payment.controller.dto.request.CreateOrUpdateCardRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.CardResponseDTO;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;
import com.nonsoolmate.toss.service.TossPaymentService;
import com.nonsoolmate.toss.service.vo.TossPaymentBillingVO;

@SpringBootTest
@ActiveProfiles("test")
class BillingServiceTest {
	@InjectMocks BillingService billingService;

	@Mock BillingRepository billingRepository;
	@Mock MemberRepository memberRepository;
	@Mock TossPaymentService tossPaymentService;

	private static final String MEMBER_ID = "testMemberId";
	private static final String FAKE_MEMBER_ID = "fakeMemberId";
	private static final String TEST_AUTH_KEY = "testAuthKey";
	private static final String TEST_FAKE_AUTH_KEY = "fakeTestAuthKey";
	private static final String TEST_CARD_COMPANY = "testCardCompany";
	private static final String TEST_CARD_NUMBER = "testCardNumber";
	private static final String TEST_BILLING_KEY = "testBillingKey";
	private static final String NOT_REGISTERED_CARD_EXCEPTION_MESSAGE = "사용자가 카드를 등록하지 않았습니다";
	private static final String NOT_FOUND_MEMBER_EXCEPTION_MESSAGE = "존재하지 않는 유저입니다.";
	private static final String TOSS_PAYMENT_ISSUE_BILLING_EXCEPTION_MESSAGE = "카드 등록에 실패했습니다";

	@Test
	@DisplayName("사용자가 등록한 카드를 확인하는 경우")
	void getCardTestWhenMemberHasRegisteredCard() {
		// given
		Member expectedMember = getExpectedMember();
		Billing expectedBilling = getExpectedBilling(expectedMember);
		CardResponseDTO expectedResponse = getExpectedCardResponseDTO(expectedBilling);
		given(billingRepository.findByCustomerIdOrThrow(anyString())).willReturn(expectedBilling);

		// when
		CardResponseDTO response = billingService.getCard(MEMBER_ID);

		// then
		Assertions.assertThat(response).isEqualTo(expectedResponse);
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

	@Test
	@DisplayName("사용자가 카드를 등록하는 경우")
	void registerCardTest() {
		// given
		Member expectedMember = getExpectedMember();
		CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
				new CreateOrUpdateCardRequestDTO(MEMBER_ID, TEST_AUTH_KEY);
		TossPaymentBillingVO mockTossPaymentBillingVO =
				TossPaymentBillingVO.of(
						expectedMember.getMemberId(), TEST_BILLING_KEY, TEST_CARD_COMPANY, TEST_CARD_NUMBER);
		Billing expectedBilling = getExpectedBilling(expectedMember);
		CardResponseDTO expectedResponse = getExpectedCardResponseDTO(expectedBilling);

		given(memberRepository.findByMemberIdOrThrow(anyString())).willReturn(expectedMember);
		given(tossPaymentService.issueBilling(any(), any())).willReturn(mockTossPaymentBillingVO);
		given(billingRepository.save(any())).willReturn(expectedBilling);

		// when
		CardResponseDTO response = billingService.registerCard(createOrUpdateCardRequestDTO);

		// then
		Assertions.assertThat(response).isEqualTo(expectedResponse);
	}

	@Test
	@DisplayName("사용자가 customerKey를 조작하여 카드를 등록하는 경우")
	void registerCardTestWithWrongCustomerKey() {
		// given
		CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
				new CreateOrUpdateCardRequestDTO(FAKE_MEMBER_ID, TEST_AUTH_KEY);
		given(memberRepository.findByMemberIdOrThrow(anyString()))
				.willThrow(new AuthException(NOT_FOUND_MEMBER));

		// when, then
		Assertions.assertThatThrownBy(() -> billingService.registerCard(createOrUpdateCardRequestDTO))
				.isInstanceOf(AuthException.class)
				.hasMessage(NOT_FOUND_MEMBER_EXCEPTION_MESSAGE);
	}

	@Test
	@DisplayName("사용자가 authKey를 조작하여 카드를 등록하는 경우")
	void registerCardTestWithWrongAuthKey() {
		// given
		CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
				new CreateOrUpdateCardRequestDTO(MEMBER_ID, TEST_FAKE_AUTH_KEY);
		Member expectedMember = getExpectedMember();
		given(memberRepository.findByMemberIdOrThrow(anyString())).willReturn(expectedMember);
		given(tossPaymentService.issueBilling(any(), any()))
				.willThrow(new BillingException(TOSS_PAYMENT_ISSUE_BILLING));

		// when, then
		Assertions.assertThatThrownBy(() -> billingService.registerCard(createOrUpdateCardRequestDTO))
				.isInstanceOf(BillingException.class)
				.hasMessage(TOSS_PAYMENT_ISSUE_BILLING_EXCEPTION_MESSAGE);
	}

	@Test
	@DisplayName("사용자가 customerKey와 AuthKey를 조작하여 카드를 등록하는 경우")
	void registerCardTestWithWrongCustomerKeyAndAuthKey() {
		CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
				new CreateOrUpdateCardRequestDTO(FAKE_MEMBER_ID, TEST_AUTH_KEY);
		given(memberRepository.findByMemberIdOrThrow(anyString()))
				.willThrow(new AuthException(NOT_FOUND_MEMBER));

		// when, then
		Assertions.assertThatThrownBy(() -> billingService.registerCard(createOrUpdateCardRequestDTO))
				.isInstanceOf(AuthException.class)
				.hasMessage(NOT_FOUND_MEMBER_EXCEPTION_MESSAGE);
	}

	@Test
	@DisplayName("사용자가 카드를 변경하는 경우")
	void updateCardTest() {
		// given
		Member expectedMember = getExpectedMember();

		CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
				new CreateOrUpdateCardRequestDTO(MEMBER_ID, TEST_AUTH_KEY);
		TossPaymentBillingVO mockTossPaymentBillingVO =
				TossPaymentBillingVO.of(
						expectedMember.getMemberId(), TEST_BILLING_KEY, TEST_CARD_COMPANY, TEST_CARD_NUMBER);
		Billing expectedBilling = getExpectedBilling(expectedMember);
		CardResponseDTO expectedResponse = getExpectedCardResponseDTO(expectedBilling);

		given(billingRepository.findByCustomerIdOrThrow(anyString())).willReturn(expectedBilling);
		given(tossPaymentService.issueBilling(any(), any())).willReturn(mockTossPaymentBillingVO);

		// when
		CardResponseDTO response = billingService.updateCard(createOrUpdateCardRequestDTO);

		// then
		Assertions.assertThat(response).isEqualTo(expectedResponse);
	}

	@Test
	@DisplayName("사용자가 카드 등록을 하지 않고 카드 변경을 요청하는 경우")
	void updateCardTestWhenMemberHasNotRegisteredCard() {
		// given
		CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
				new CreateOrUpdateCardRequestDTO(MEMBER_ID, TEST_AUTH_KEY);
		given(billingRepository.findByCustomerIdOrThrow(anyString()))
				.willThrow(new BillingException(NOT_FOUND_BILLING));

		// when, then
		Assertions.assertThatThrownBy(() -> billingService.updateCard(createOrUpdateCardRequestDTO))
				.isInstanceOf(BillingException.class)
				.hasMessage(NOT_REGISTERED_CARD_EXCEPTION_MESSAGE);
	}

	@Test
	@DisplayName("사용자가 customerKey를 조작하여 카드 변경을 요청하는 경우")
	void updateCardTestWithWrongCustomerKey() {
		// given
		CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
				new CreateOrUpdateCardRequestDTO(FAKE_MEMBER_ID, TEST_AUTH_KEY);
		given(billingRepository.findByCustomerIdOrThrow(anyString()))
				.willThrow(new BillingException(NOT_FOUND_BILLING));

		// when, then
		Assertions.assertThatThrownBy(() -> billingService.updateCard(createOrUpdateCardRequestDTO))
				.isInstanceOf(BillingException.class)
				.hasMessage(NOT_REGISTERED_CARD_EXCEPTION_MESSAGE);
	}

	private Member getExpectedMember() {
		return Member.builder()
				.email("testEmail")
				.name("testName")
				.platformType(PlatformType.NAVER)
				.platformId("testPlatformId")
				.role(Role.USER)
				.birthYear("test")
				.build();
	}

	private Billing getExpectedBilling(Member expectedMember) {
		return Billing.builder()
				.billingKey(TEST_BILLING_KEY)
				.cardCompany(TEST_CARD_COMPANY)
				.cardNumber(TEST_CARD_NUMBER)
				.customer(expectedMember)
				.build();
	}

	private CardResponseDTO getExpectedCardResponseDTO(Billing billing) {
		return CardResponseDTO.of(
				billing.getBillingId(), billing.getCardCompany(), billing.getCardNumber());
	}
}
