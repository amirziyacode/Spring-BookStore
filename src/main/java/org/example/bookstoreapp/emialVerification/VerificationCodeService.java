package org.example.bookstoreapp.emialVerification;

import lombok.AllArgsConstructor;
import org.example.bookstoreapp.user.User;
import org.example.bookstoreapp.user.UserRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationCodeService {

    private final VerificationRepository verificationRepository;
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

    private static boolean isCodeActiveAndNotExpired(VerificationCode byCodeAndUserEmail) {
        return !LocalDateTime.now().isAfter(byCodeAndUserEmail.getExpireTime()) && !byCodeAndUserEmail.getUsed();
    }
}
