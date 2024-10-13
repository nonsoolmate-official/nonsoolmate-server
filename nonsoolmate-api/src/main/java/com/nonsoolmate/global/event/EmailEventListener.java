package com.nonsoolmate.global.event;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nonsoolmate.email.service.EmailService;

@Component
@RequiredArgsConstructor
public class EmailEventListener {
  private final EmailService emailService;

  @Async
  @EventListener
  public void publishExamRecordStatusUpdatedEvent(ExamRecordStatusUpdatedEvent event) {
    emailService.sendMessageAboutExamRecordStatus(
        event.email(), event.editingType(), event.examFullName());
  }
}
