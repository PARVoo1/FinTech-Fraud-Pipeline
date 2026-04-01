package net.parvkhandelwal.journalApp.service;


import net.parvkhandelwal.service.EmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
     void testEmailService() {
        emailService.sendEmail("parv10046@gmail.com","Test Subject","Hello form Parv to Parv ");
        Assertions.assertTrue(true);
    }
}
