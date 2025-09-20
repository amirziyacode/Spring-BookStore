package org.example.bookstoreapp.emialVerification;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookstoreapp.user.User;

import java.time.LocalDateTime;

@Entity(name = "_verificatonCode")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @OneToOne
    private User user;

    private LocalDateTime expireTime;

    private Boolean used;
}
