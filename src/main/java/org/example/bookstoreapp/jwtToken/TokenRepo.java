package org.example.bookstoreapp.jwtToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Integer> {
    @Query("SELECT  t FROM jwtToken t inner join User  u on t.user.id = u.id  where  u.id = :userId and (t.revoked = false )")
    List<Token> findAllValidTokensByUser(Integer userId);
    Optional<Token> findByToken(String token);
}
