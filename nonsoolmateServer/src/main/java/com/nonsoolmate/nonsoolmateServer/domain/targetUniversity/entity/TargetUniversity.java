package com.nonsoolmate.nonsoolmateServer.domain.targetUniversity.entity;

import com.nonsoolmate.nonsoolmateServer.domain.member.entity.Member;
import com.nonsoolmate.nonsoolmateServer.domain.university.entity.University;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TargetUniversity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long targetUniversityId;

	@ManyToOne
	University university;

	@ManyToOne
	Member member;
}
