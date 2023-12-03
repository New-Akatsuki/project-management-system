//package org.blank.projectmanagementsystem.service.impl;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.blank.projectmanagementsystem.domain.entity.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//import org.powermock.reflect.Whitebox;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//@PrepareForTest({MailServiceImpl.class, MimeMessage.class})
//public class MailServiceImplTest {
//    @Mock
//    private JavaMailSender javaMailSender;
//
//    @InjectMocks
//    private MailServiceImpl mailService;
//
//    @BeforeEach
//    void setUp() {
//        // No need for MockitoAnnotations.initMocks(this) with @ExtendWith(MockitoExtension.class)
//    }
//
//    @Test
//    public void testSendDefaultPassword() throws Exception {
//        User user = new User();
//        user.setEmail("test@example.com");
//        user.setName("John Doe");
//
//        String defaultPassword = "default123";
//
//        MimeMessage mimeMessage = PowerMockito.mock(MimeMessage.class);
//        MimeMessageHelper mimeMessageHelper = PowerMockito.mock(MimeMessageHelper.class);
//
//        // Set the value for fromEmail in the MailServiceImpl instance
//        Whitebox.setInternalState(mailService, "fromEmail", "your@email.com");
//
//        PowerMockito.when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//        PowerMockito.whenNew(MimeMessageHelper.class).withAnyArguments().thenReturn(mimeMessageHelper);
//
//        assertDoesNotThrow(() -> mailService.sendDefaultPassword(user, defaultPassword));
//
//    }
//
//}
