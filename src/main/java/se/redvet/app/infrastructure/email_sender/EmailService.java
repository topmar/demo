package se.redvet.app.infrastructure.email_sender;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.redvet.app.infrastructure.text_literal.TextLiteral;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSenderImpl mailSender;
    @Value("${email.from}")
    private String from;
    @Value("${email.subject}")
    private String subject;
    @Value("${verification.url}")
    private String verificationUrl;

    @Async
    public void sendEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(String.format(TextLiteral.EMAIL_TEXT, verificationUrl, token));
        mailSender.send(message);
    }
}
