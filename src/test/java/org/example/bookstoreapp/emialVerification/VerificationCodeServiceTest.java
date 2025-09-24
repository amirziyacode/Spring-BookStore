package org.example.bookstoreapp.emialVerification;

import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceTest {

    @InjectMocks
    private VerificationCodeService verificationCodeService;

    @Mock private VerificationCodeRepository verificationCodeRepository;
    @Mock private EmailService emailService;
    @Mock private UserRepo userRepo;
    private User user;

    private final static String CODE_TEST = "code_test";
    private final static String EMAIL = "test@gmail.com";


    @BeforeEach
    void setUp() {
        user  = User.builder()
                .email(EMAIL)
                .fullName("test")
                .isActive(false)
                .password("test")
                .build();
    }



    @Test
    void should_generateVerificationCode_and_save_in_database() {


        verificationCodeService.generateVerificationCode(user);
        ArgumentCaptor<VerificationCode> captor = ArgumentCaptor.forClass(VerificationCode.class);
        verify(verificationCodeRepository,times(1)).save(captor.capture());
        VerificationCode verificationCode = captor.getValue();

        assertThat(verificationCode.getUser()).isEqualTo(user);
        assertThat(verificationCode.getCode()).hasSize(6);
        assertThat(verificationCode.getUsed()).isFalse();
        assertThat(verificationCode.getExpireTime()).isAfter(LocalDateTime.now());


    }

    @Test
    void should_verifyCode_successfully() {

        VerificationCode verificationCode = VerificationCode.builder()
                .code(CODE_TEST)
                .used(false)
                .user(user)
                .expireTime(LocalDateTime.now().plusMinutes(5))
                .build();


        when(verificationCodeRepository.findByCodeAndUserEmail(CODE_TEST, EMAIL)).thenReturn(Optional.ofNullable(verificationCode));

        boolean verified = verificationCodeService.verifyCode(EMAIL, CODE_TEST);


        assertThat(verified).isTrue();
        assertThat(Objects.requireNonNull(verificationCode).getUsed()).isTrue();
        assertThat(user.isActive()).isTrue();
        verify(userRepo,times(1)).save(user);
        verify(verificationCodeRepository,times(1)).findByCodeAndUserEmail(anyString(), anyString());
    }

    @Test
    void should_verifyCode_fail_expiredTime() {
        VerificationCode verificationCode = VerificationCode.builder()
                .code(CODE_TEST)
                .used(false)
                .user(user)
                .expireTime(LocalDateTime.now().minusMinutes(5))
                .build();

        when(verificationCodeRepository.findByCodeAndUserEmail(CODE_TEST, EMAIL)).thenReturn(Optional.ofNullable(verificationCode));

        boolean verified = verificationCodeService.verifyCode(EMAIL, CODE_TEST);

        assertThat(verified).isFalse();
        assertThat(Objects.requireNonNull(verificationCode).getUsed()).isFalse();
        assertThat(user.isActive()).isFalse();
        verify(verificationCodeRepository,times(1)).findByCodeAndUserEmail(anyString(), anyString());
    }

    @Test
    void should_verifyCode_Not_found_user() {
        when(verificationCodeRepository.findByCodeAndUserEmail(CODE_TEST, EMAIL)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> verificationCodeService.verifyCode(EMAIL,CODE_TEST)).isInstanceOf(UsernameNotFoundException.class).hasMessage("User not found");
    }

    @Test
    void resendCode_not_found_user() {
        when(userRepo.findByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> verificationCodeService.resendCode(EMAIL)).isInstanceOf(UsernameNotFoundException.class).hasMessage("User not found");
    }

    @Test
    void resendCode_has_fail_cause_user_isActive(){
        user.setActive(true);
        when(userRepo.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> verificationCodeService.resendCode(EMAIL)).isInstanceOf(IllegalStateException.class).hasMessage("User is already active");
    }

    @Test
    void resendCode_successfully() {
        VerificationCode verificationCode = VerificationCode.builder()
                .code(CODE_TEST)
                .used(false)
                .user(user)
                .expireTime(LocalDateTime.now().minusMinutes(5))
                .build();


        when(userRepo.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(verificationCodeRepository.findByUserEmail(EMAIL)).thenReturn(Collections.singletonList(verificationCode));
        when(verificationCodeRepository.save(any(VerificationCode.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        doNothing().when(emailService).sendVarificationCode(eq(EMAIL),anyString());

        verificationCodeService.resendCode(EMAIL);

        assertThat(verificationCode.getUsed()).isTrue();
        verify(verificationCodeRepository,times(2)).save(any(VerificationCode.class));
        verify(emailService,times(1)).sendVarificationCode(eq(EMAIL), anyString());
    }
}
