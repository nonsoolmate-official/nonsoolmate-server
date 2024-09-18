package com.nonsoolmate.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import com.nonsoolmate.exception.member.MemberException;
import com.nonsoolmate.exception.member.MemberExceptionType;
import com.nonsoolmate.member.entity.enums.PlatformType;
import com.nonsoolmate.member.entity.enums.Role;

import io.hypersistence.utils.hibernate.id.Tsid;

@Entity
@Table(
		uniqueConstraints = {
			@UniqueConstraint(
					name = "UK_PLATFORM_TYPE_PLATFORM_ID",
					columnNames = {"platformType", "platformId"})
		})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
	@Id @Tsid private String memberId;

	@NotNull private String email;

	@NotNull private String name;

	@Enumerated(EnumType.STRING)
	@NotNull
	private PlatformType platformType;

	@NotNull private String platformId;

	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;

	@Length(max = 4)
	private String birthYear;

	@Length(max = 1)
	private String gender;

	@NotNull
	@Length(max = 13)
	private String phoneNumber;

	private int reviewTicketCount;

	private int reReviewTicketCount;

	@Builder
	public Member(
			final String email,
			final String name,
			final PlatformType platformType,
			final String platformId,
			final Role role,
			final String birthYear,
			final String gender,
			final String phoneNumber) {
		this.email = email;
		this.name = name;
		this.platformType = platformType;
		this.platformId = platformId;
		this.role = role;
		this.birthYear = birthYear;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
	}

	public void decreaseReviewTicket() {
		if (this.reviewTicketCount <= 0) {
			throw new MemberException(MemberExceptionType.MEMBER_USE_TICKET_FAIL);
		}
		this.reviewTicketCount -= 1;
	}

	public void decreaseReReviewTicket() {
		if (this.reReviewTicketCount <= 0) {
			throw new MemberException(MemberExceptionType.MEMBER_USE_TICKET_FAIL);
		}
		this.reReviewTicketCount -= 1;
	}
}
