package com.nonsoolmate.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class University {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long universityId;

  @NotNull private String universityName;

  @NotNull private String universityImageUrl;

  @Builder
  public University(String universityName, String universityImageUrl) {
    this.universityName = universityName;
    this.universityImageUrl = universityImageUrl;
  }
}
