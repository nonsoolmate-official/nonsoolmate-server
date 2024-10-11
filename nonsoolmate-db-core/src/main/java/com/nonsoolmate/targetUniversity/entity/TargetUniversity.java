package com.nonsoolmate.targetUniversity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.nonsoolmate.member.entity.Member;
import com.nonsoolmate.university.entity.University;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TargetUniversity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long targetUniversityId;

  @ManyToOne private University university;

  @ManyToOne private Member member;

  public TargetUniversity(University university, Member member) {
    this.university = university;
    this.member = member;
  }
}
