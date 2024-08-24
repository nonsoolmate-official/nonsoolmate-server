package com.nonsoolmate.nonsoolmateServer.domain.member.entity;

import java.time.LocalDateTime;

import com.nonsoolmate.nonsoolmateServer.domain.common.BaseTimeEntity;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.MembershipStatus;
import com.nonsoolmate.nonsoolmateServer.domain.member.entity.enums.MembershipType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membership extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long membershipId;

	@OneToOne
	Member member;

	@Enumerated(EnumType.STRING)
	MembershipType membershipType;

	LocalDateTime startDate;

	LocalDateTime endDate;

	@Enumerated(EnumType.STRING)
	MembershipStatus status;
}
