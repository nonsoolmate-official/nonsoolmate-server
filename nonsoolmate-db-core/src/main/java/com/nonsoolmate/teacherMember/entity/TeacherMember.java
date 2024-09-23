package com.nonsoolmate.teacherMember.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.teacher.entity.Teacher;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TeacherMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long teacherMemberId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	@NotNull
	private Teacher teacher;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	@NotNull
	private Member member;
}
