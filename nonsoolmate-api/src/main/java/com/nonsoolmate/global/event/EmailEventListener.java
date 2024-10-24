package com.nonsoolmate.global.event;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.nonsoolmate.email.service.EmailService;

@Component
@RequiredArgsConstructor
public class EmailEventListener {
  private final EmailService emailService;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void publishExamRecordStatusUpdatedEvent(ExamRecordStatusUpdatedEvent event) {
    emailService.sendMessageAboutExamRecordStatus(
        event.email(), event.editingType(), event.examFullName());
  }
}
