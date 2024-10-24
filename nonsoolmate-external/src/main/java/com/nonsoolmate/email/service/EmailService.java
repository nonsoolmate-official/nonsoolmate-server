package com.nonsoolmate.email.service;

import static com.nonsoolmate.exception.common.CommonErrorType.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.nonsoolmate.exception.common.BusinessException;

@Service
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;
  private final SpringTemplateEngine templateEngine;

  @Value("${spring.mail.username}")
  private String fromEmail;

  private static final String SUBJECT_STRING_TEMPLATE = "🔔 %s %s이 완료되었습니다.";

  public void sendMessageAboutExamRecordStatus(
      String email, String editingType, String examFullName) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    try {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      messageHelper.setTo(email);
      messageHelper.setFrom(fromEmail);

      String subject = SUBJECT_STRING_TEMPLATE.formatted(examFullName, editingType);
      messageHelper.setSubject(subject);

      String content = buildEmailContent(editingType, examFullName);

      messageHelper.setText(content, true);

      mailSender.send(mimeMessage);

    } catch (RuntimeException | MessagingException e) {
      throw new BusinessException(EXTERNAL_SERVER_ERROR);
    }
  }

  public String buildEmailContent(String universityName, String examName) {
    Context context = new Context();
    context.setVariable("editingType", universityName);
    context.setVariable("examFullName", examName);

    return templateEngine.process("examRecordStatusTemplate", context);
  }
}
