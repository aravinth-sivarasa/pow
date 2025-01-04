package io.pow.web.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    JavaMailSender mailSender;
   
    public void sendEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("aravinth.sivarasa@basicneeds.io");
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        logger.info("Email send successfully to: " + email);
    }
}
