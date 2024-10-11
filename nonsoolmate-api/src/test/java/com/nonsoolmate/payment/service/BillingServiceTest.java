package com.nonsoolmate.payment.service;

import static com.nonsoolmate.exception.payment.BillingExceptionType.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

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
import com.nonsoolmate.member.repository.MemberRepository;
import com.nonsoolmate.payment.controller.dto.request.CreateOrUpdateCardRequestDTO;
import com.nonsoolmate.payment.controller.dto.response.CardResponseDTO;
import com.nonsoolmate.payment.entity.Billing;
import com.nonsoolmate.payment.repository.BillingRepository;
import com.nonsoolmate.toss.service.TossPaymentService;
import com.nonsoolmate.toss.service.vo.TossPaymentBillingKeyVO;

@SpringBootTest
@ActiveProfiles("test")
class BillingServiceTest {
  @InjectMocks BillingService billingService;

  @Mock BillingRepository billingRepository;
  @Mock MemberRepository memberRepository;
  @Mock TossPaymentService tossPaymentService;

  private static final String MEMBER_ID = "testMemberId";
  private static final String TEST_AUTH_KEY = "testAuthKey";
  private static final String TEST_FAKE_AUTH_KEY = "fakeTestAuthKey";
  private static final String TEST_CARD_COMPANY = "testCardCompany";
  private static final String TEST_CARD_NUMBER = "testCardNumber";
  private static final String TEST_BILLING_KEY = "testBillingKey";
  private static final String NOT_REGISTERED_CARD_EXCEPTION_MESSAGE = "사용자가 카드를 등록하지 않았습니다";
  private static final String TOSS_PAYMENT_ISSUE_BILLING_EXCEPTION_MESSAGE = "카드 등록에 실패했습니다";

  @Test
  @DisplayName("사용자가 등록한 카드를 확인하는 경우")
  void getCardTestWhenMemberHasRegisteredCard() {
    // given
    Member expectedMember = getExpectedMember();
    Billing expectedBilling = getExpectedBilling(expectedMember);
    CardResponseDTO expectedResponse = getExpectedCardResponseDTO(expectedBilling);
    given(billingRepository.findByCustomerMemberId(anyString()))
        .willReturn(Optional.of(expectedBilling));

    // when
    CardResponseDTO response = billingService.getCard(MEMBER_ID);

    // then
    Assertions.assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  @DisplayName("사용자가 카드를 등록하지 않은 경우")
  void getCardTestWhenMemberHasNotRegisteredCard() {
    // given
    given(billingRepository.findByCustomerMemberId(anyString())).willReturn(Optional.empty());

    // when, then
    Assertions.assertThat(billingService.getCard(MEMBER_ID)).isNull();
  }

  @Test
  @DisplayName("사용자가 카드를 등록하는 경우")
  void registerCardTest() {
    // given
    Member expectedMember = getExpectedMember();
    CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
        new CreateOrUpdateCardRequestDTO(TEST_AUTH_KEY);
    TossPaymentBillingKeyVO mockTossPaymentBillingKeyVO =
        TossPaymentBillingKeyVO.of(
            expectedMember.getMemberId(), TEST_BILLING_KEY, TEST_CARD_COMPANY, TEST_CARD_NUMBER);
    Billing expectedBilling = getExpectedBilling(expectedMember);
    CardResponseDTO expectedResponse = getExpectedCardResponseDTO(expectedBilling);

    given(memberRepository.findByMemberIdOrThrow(anyString())).willReturn(expectedMember);
    given(tossPaymentService.issueBillingKey(any(), any())).willReturn(mockTossPaymentBillingKeyVO);
    given(billingRepository.save(any())).willReturn(expectedBilling);

    // when
    CardResponseDTO response = billingService.registerCard(createOrUpdateCardRequestDTO, MEMBER_ID);

    // then
    Assertions.assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  @DisplayName("사용자가 authKey를 조작하여 카드를 등록하는 경우")
  void registerCardTestWithWrongAuthKey() {
    // given
    CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
        new CreateOrUpdateCardRequestDTO(TEST_FAKE_AUTH_KEY);
    Member expectedMember = getExpectedMember();
    given(memberRepository.findByMemberIdOrThrow(anyString())).willReturn(expectedMember);
    given(tossPaymentService.issueBillingKey(any(), any()))
        .willThrow(new BillingException(TOSS_PAYMENT_ISSUE_BILLING));

    // when, then
    Assertions.assertThatThrownBy(
            () -> billingService.registerCard(createOrUpdateCardRequestDTO, MEMBER_ID))
        .isInstanceOf(BillingException.class)
        .hasMessage(TOSS_PAYMENT_ISSUE_BILLING_EXCEPTION_MESSAGE);
  }

  @Test
  @DisplayName("사용자가 카드를 변경하는 경우")
  void updateCardTest() {
    // given
    Member expectedMember = getExpectedMember();

    CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
        new CreateOrUpdateCardRequestDTO(TEST_AUTH_KEY);
    TossPaymentBillingKeyVO mockTossPaymentBillingKeyVO =
        TossPaymentBillingKeyVO.of(
            expectedMember.getMemberId(), TEST_BILLING_KEY, TEST_CARD_COMPANY, TEST_CARD_NUMBER);
    Billing expectedBilling = getExpectedBilling(expectedMember);
    CardResponseDTO expectedResponse = getExpectedCardResponseDTO(expectedBilling);

    given(billingRepository.findByCustomerIdOrThrow(anyString())).willReturn(expectedBilling);
    given(tossPaymentService.issueBillingKey(any(), any())).willReturn(mockTossPaymentBillingKeyVO);

    // when
    CardResponseDTO response = billingService.updateCard(createOrUpdateCardRequestDTO, MEMBER_ID);

    // then
    Assertions.assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  @DisplayName("사용자가 카드 등록을 하지 않고 카드 변경을 요청하는 경우")
  void updateCardTestWhenMemberHasNotRegisteredCard() {
    // given
    CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
        new CreateOrUpdateCardRequestDTO(TEST_AUTH_KEY);
    given(billingRepository.findByCustomerIdOrThrow(anyString()))
        .willThrow(new BillingException(NOT_FOUND_BILLING));

    // when, then
    Assertions.assertThatThrownBy(
            () -> billingService.updateCard(createOrUpdateCardRequestDTO, MEMBER_ID))
        .isInstanceOf(BillingException.class)
        .hasMessage(NOT_REGISTERED_CARD_EXCEPTION_MESSAGE);
  }

  @Test
  @DisplayName("사용자가 authKey를 조작하여 카드 변경을 요청하는 경우")
  void updateCardTestWithWrongAuthKey() {
    // given
    CreateOrUpdateCardRequestDTO createOrUpdateCardRequestDTO =
        new CreateOrUpdateCardRequestDTO(TEST_FAKE_AUTH_KEY);
    Member expectedMember = getExpectedMember();
    Billing expectedBilling = getExpectedBilling(expectedMember);
    given(billingRepository.findByCustomerIdOrThrow(anyString())).willReturn(expectedBilling);
    given(tossPaymentService.issueBillingKey(any(), any()))
        .willThrow(new BillingException(TOSS_PAYMENT_ISSUE_BILLING));

    // when, then
    Assertions.assertThatThrownBy(
            () -> billingService.updateCard(createOrUpdateCardRequestDTO, MEMBER_ID))
        .isInstanceOf(BillingException.class)
        .hasMessage(TOSS_PAYMENT_ISSUE_BILLING_EXCEPTION_MESSAGE);
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
