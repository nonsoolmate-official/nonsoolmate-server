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

  public void sendMessage(String email) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();

    try {
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      // 메일을 받을 수신자 설정
      mimeMessageHelper.setTo(email);
      mimeMessageHelper.setFrom(emailConfig.getMailServerUsername());

      // 메일의 제목 설정

      mimeMessageHelper.setSubject("html 적용 테스트 메일 제목");

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

      mimeMessageHelper.setText(content, true);

      mailSender.send(mimeMessage);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
