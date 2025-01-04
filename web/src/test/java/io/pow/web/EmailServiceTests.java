package io.pow.web;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.pow.web.util.EmailService;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    EmailService emailService;
    @Test
    void sendEmail() throws Exception {
        String uuid = UUID.randomUUID().toString();
        emailService.sendEmail("aravinth.sivarasa@gmail.com", "Test email: "+uuid, "Test body "+uuid);

    }
    
}
