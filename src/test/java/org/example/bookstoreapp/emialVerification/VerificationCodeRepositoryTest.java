package org.example.bookstoreapp.emialVerification;

import org.example.bookstoreapp.user.Role;
import org.example.bookstoreapp.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class VerificationCodeRepositoryTest {


    @Autowired
    private VerificationCodeRepository verificationCodeRepository;


    @Autowired
    private TestEntityManager testEntityManager;

    private User user;
    private final static String EMAIL = "test@gmail.com";
    private final static String CODE = "code_test";


    @BeforeEach
    void setUp() {

        user = User.builder()
                .fullName("user")
                .password("password")
                .email(EMAIL)
                .role(Role.USER)
                .build();
        testEntityManager.persistAndFlush(user);

        VerificationCode verificationCode = VerificationCode.builder()
                .code(CODE)
                .used(false)
                .user(user)
                .build();

        testEntityManager.persistAndFlush(verificationCode);
    }

    @Test
    void findByCodeAndUserEmail() {
        Optional<VerificationCode> byCodeAndUserEmail = verificationCodeRepository.findByCodeAndUserEmail(CODE, EMAIL);

        assertThat(byCodeAndUserEmail.isPresent()).isTrue();
        assertThat(byCodeAndUserEmail.get().getCode()).isEqualTo(CODE);
    }

    @Test
    void should_findByUserEmail_thenReturn_List_of_verification_codes() {


        VerificationCode secondeCode = VerificationCode.builder()
                .code(CODE)
                .used(false)
                .user(user)
                .build();

        testEntityManager.persistAndFlush(secondeCode);

        List<VerificationCode> verificationCodes = verificationCodeRepository.findByUserEmail(EMAIL);

        assertThat(verificationCodes).hasSize(2);
        assertThat(verificationCodes).extracting(VerificationCode::getCode).contains("code_test","code_test");
    }

    @AfterEach
    void afterEach() {
        verificationCodeRepository.deleteAll();
    }
}