package com.nonsoolmate.global.event;

public record ExamRecordStatusUpdatedEvent(String email, String editingType, String examFullName) {
  public static ExamRecordStatusUpdatedEvent of(
      String email, String editingType, String examFullName) {
    return new ExamRecordStatusUpdatedEvent(email, editingType, examFullName);
  }
}
