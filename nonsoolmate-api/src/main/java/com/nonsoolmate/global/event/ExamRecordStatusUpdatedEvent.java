package com.nonsoolmate.global.event;

public record ExamRecordStatusUpdatedEvent(String email, String status, String examFullName) {
  public static ExamRecordStatusUpdatedEvent of(String email, String status, String examFullName) {
    return new ExamRecordStatusUpdatedEvent(email, status, examFullName);
  }
}
