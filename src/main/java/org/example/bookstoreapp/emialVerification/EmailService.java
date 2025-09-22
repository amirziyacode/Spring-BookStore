package org.example.bookstoreapp.emialVerification;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendVarificationCode(String to, String code){
        String subject = "Account Verification Code";
        String body = "<h2>Welcome to the Spring Book Store!</h2>"
                + "<p>Your verification code is: <b>" + code + "</b></p>"
                + "<p>This code will expire in 5 minutes.</p>";

        MimeMessage message = mailSender.createMimeMessage();
        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        }catch (Exception e){
            throw new RuntimeException("Failed to send email",e);
        }
    }

}
