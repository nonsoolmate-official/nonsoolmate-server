package com.nonsoolmate.member.entity;

import static com.nonsoolmate.exception.member.MembershipExceptionType.TERMINATED_MEMBERSHIP;

import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.common.BaseTimeEntity;
import com.nonsoolmate.exception.member.MemberException;
import com.nonsoolmate.member.entity.enums.MembershipStatus;
import com.nonsoolmate.member.entity.enums.MembershipType;
import com.nonsoolmate.order.entity.OrderDetail;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership extends BaseTimeEntity {
  private static final int MEMBERSHIP_PERIOD = 27;
  private static final int DAY_1 = 1;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long membershipId;

  @OneToOne private Member member;

  @Enumerated(EnumType.STRING)
  private MembershipType membershipType;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  @Enumerated(EnumType.STRING)
  private MembershipStatus status;

  @Builder
  private Membership(final Member member, final MembershipType membershipType) {
    this.member = member;
    this.membershipType = membershipType;
    this.startDate = LocalDateTime.now();
    this.endDate = LocalDateTime.now().plusDays(MEMBERSHIP_PERIOD);
    this.status = MembershipStatus.IN_PROGRESS;
  }

  public void updateMembershipType(MembershipType membershipType) {
    this.membershipType = membershipType;
  }

  public void validateMembershipStatus() {
    if (this.status.equals(MembershipStatus.TERMINATED)) {
      throw new MemberException(TERMINATED_MEMBERSHIP);
    }
  }

  public LocalDateTime getExpectedPaymentDate(Optional<OrderDetail> orderDetail) {
    if (orderDetail.isEmpty()) {
      return null;
    }

    return this.endDate.plusDays(DAY_1);
  }

  public void changeMembershipStatus(MembershipStatus status) {
    this.status = status;
  }

  public boolean isExpiredMemberShip() {
    if (this.endDate.isAfter(LocalDateTime.now())) {
      return true;
    }

    return false;
  }
}
