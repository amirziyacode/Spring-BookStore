package org.example.bookstoreapp.emialVerification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    @Query("SELECT v from _verificatonCode v where v.code = :code and v.user.email = :email")
    Optional<VerificationCode> findByCodeAndUserEmail(@Param("code") String code,@Param("email") String email);

    List<VerificationCode> findByUserEmail(@Param("email") String email);
}
