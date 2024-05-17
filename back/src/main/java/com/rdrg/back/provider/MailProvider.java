package com.rdrg.back.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailProvider {
    
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender javaMailSender;

    public void mailSend (String to, String authNumber) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(from);
        mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject("estate 인증 번호");
        mimeMessage.setText(getText(authNumber), "utf-8", "html");
        javaMailSender.send(mimeMessage);
    }

    private String getText(String authNumber) {
        String text = 
            "<h2 style='text-align: center'>rdrg 인증 번호</h2>" + 
            "<p>요청하신 email 인증 번호는 <strong style='color: red'>" +
            authNumber + 
            "</strong>입니다.</p>";
        return text;
    }
}
