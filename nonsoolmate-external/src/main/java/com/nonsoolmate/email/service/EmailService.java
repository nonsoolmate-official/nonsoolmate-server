package com.nonsoolmate.email.service;

import jakarta.mail.internet.MimeMessage;

import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.nonsoolmate.email.config.EmailConfig;

@Service
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;
  private final EmailConfig emailConfig;

  public void sendMessageAboutExamRecordStatus(
      String email, String editingType, String examFullName) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    try {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      messageHelper.setTo(email);
      messageHelper.setFrom(emailConfig.getMailServerUsername());

      // 메일의 제목 설정
      String subject = "[논술메이트] " + examFullName + " " + editingType + "이 완료되었습니다.";
      messageHelper.setSubject(subject);

      // html 문법 적용한 메일의 내용
      String content =
          """
                    <!DOCTYPE html>
                    <html xmlns:th="http://www.thymeleaf.org">

                    <body>
                    <div style="margin:100px;">
                        <h1> 테스트 메일 </h1>
                        <br>


                        <div align="center" style="border:1px solid black;">
                            <h3> 테스트 메일 내용 </h3>
                        </div>
                        <br/>
                    </div>

                    </body>
                    </html>
                    """;

      messageHelper.setText(content, true);

      mailSender.send(mimeMessage);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
