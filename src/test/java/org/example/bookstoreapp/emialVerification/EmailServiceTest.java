package org.example.bookstoreapp.emialVerification;

import jakarta.mail.internet.MimeMessage;
import org.example.bookstoreapp.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private VerificationCodeService verificationCodeService;

    private MimeMessage mimeMessage;

    private final static String EMAIL = "test@gmial.com";
    private final static String CODE = "code_test";

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        mimeMessage = Mockito.mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    void should_send_verification_code_to_email() {

        emailService.sendVarificationCode(EMAIL, CODE);

        verify(mailSender,times(1)).createMimeMessage();
        verify(mailSender,times(1)).send(mimeMessage);
    }

    @Test
    void should_sendVerificationCode_to_email_throwException() {


        doThrow(RuntimeException.class).when(mailSender).send(mimeMessage);


        assertThrows(RuntimeException.class, () -> emailService.sendVarificationCode(EMAIL,CODE));

    }

    @Test
    void should_sendVerificationCode_to_email_byUser() {
        User user = User.builder()
                .email(EMAIL)
                .password("pass")
                .build();
        VerificationCode verificationCode = VerificationCode.builder()
                .user(user)
                .code(CODE)
                .used(false)
                .build();

        when(verificationCodeService.generateVerificationCode(user)).thenReturn(verificationCode);
        doNothing().when(verificationCodeService).sendVerificationCodeByEmail(user);
        verify(mailSender,times(1)).send(any(MimeMessage.class));
        verify(mailSender,times(1)).createMimeMessage();
    }
}