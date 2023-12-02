package org.blank.projectmanagementsystem.service.impl;
import jakarta.mail.internet.MimeMessage;
import org.blank.projectmanagementsystem.domain.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import static org.mockito.Mockito.*;


public class MailServiceImplTest {
    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private MailServiceImpl mailService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void testSendDefaultPassword() throws Exception {
            User user = new User();
            user.setEmail("test@example.com");
            user.setName("John Doe");

            String defaultPassword = "abc123";

        MimeMessage mimeMessage = mock(MimeMessage.class);
            MimeMessageHelper mimeMessageHelper = mock(MimeMessageHelper.class);

            when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//            whenNew(MimeMessageHelper.class).withArguments(mimeMessage).thenReturn(mimeMessageHelper);

            mailService.sendDefaultPassword(user, defaultPassword);

            verify(javaMailSender, times(1)).send(mimeMessage);
            verify(mimeMessageHelper, times(1)).setFrom(anyString());
            verify(mimeMessageHelper, times(1)).setTo("test@example.com");
            verify(mimeMessageHelper, times(1)).setSubject("Welcome to Dir-Ace Technology");
            verify(mimeMessageHelper, times(1)).setText(anyString(), eq(true));
        }


}




