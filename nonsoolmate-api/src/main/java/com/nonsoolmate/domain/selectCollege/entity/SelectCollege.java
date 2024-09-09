package com.nonsoolmate.domain.selectCollege.entity;

import com.nonsoolmate.domain.member.entity.Member;
import com.nonsoolmate.domain.university.entity.College;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectCollege {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long selectCollegeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "college_id")
	private College college;

	@Builder
	public SelectCollege(final Member member, final College college) {
		this.member = member;
		this.college = college;
	}
}
