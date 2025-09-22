package org.example.bookstoreapp.emialVerification;

import lombok.AllArgsConstructor;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationCodeService {

    private final VerificationCodeRepository verificationRepository;
    private final EmailService emailService;
    private final UserRepo userRepo;

    public VerificationCode generateVerificationCode(User user) {
            VerificationCode verificationCode = VerificationCode.builder()
                    .user(user)
                    .used(false)
                    .code(UUID.randomUUID().toString().substring(0, 6))
                    .expireTime(LocalDateTime.now().plusMinutes(5)).build();
            return verificationRepository.save(verificationCode);
    }

    public boolean verifyCode(String email, String code) {
        Optional<VerificationCode> byCodeAndUserEmail = verificationRepository.findByCodeAndUserEmail(code, email);

        if(byCodeAndUserEmail.isPresent()) {
            VerificationCode verificationCode = byCodeAndUserEmail.get();
            if(isCodeActiveAndNotExpired(verificationCode)) {
                verificationCode.setUsed(true);
                verificationRepository.save(verificationCode);

                User user = verificationCode.getUser();
                user.setActive(true);
                userRepo.save(user);

                return true;
            }
        }else{
            throw new UsernameNotFoundException("User not found");
        }

        return false;
    }

    @Transactional
    public void resendCode(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if(user.isActive()) {
            throw new IllegalStateException("User is already active");
        }

        expireAllVerificationCodes(email);
        VerificationCode verificationCode = generateVerificationCode(user);
        emailService.sendVarificationCode(email, verificationCode.getCode());
    }

    private void expireAllVerificationCodes(String email) {
        List<VerificationCode> verificationCodeByEmail = verificationRepository.findByUserEmail(email);
        verificationCodeByEmail.forEach(verificationCode -> {
            verificationCode.setUsed(true);
            verificationRepository.save(verificationCode);
        });
    }

    private static boolean isCodeActiveAndNotExpired(VerificationCode byCodeAndUserEmail) {
        return !LocalDateTime.now().isAfter(byCodeAndUserEmail.getExpireTime()) && !byCodeAndUserEmail.getUsed();
    }
}
