package org.example.bookstoreapp.jwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.bookstoreapp.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final TokenRepo tokenRepo;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        return  generateToken(new HashMap<>(),userDetails);
    }

    private String  generateToken(HashMap<String,Object> extractClaims, UserDetails userDetails) {
        return buildToken(extractClaims, userDetails,jwtExpiration);
    }

    private String buildToken(
            Map<String,Object> extractClaims,
            UserDetails userDetails,
            long jwtExpiration
    ){
        return  Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInkey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractClaim(token, Claims::getSubject);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setAllowedClockSkewSeconds(5)
                .setSigningKey(getSignInkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void revokeAllUserTokens(User user) {
        List<Token> allValidTokensByUser = tokenRepo.findAllValidTokensByUser(user.getId());
        if(allValidTokensByUser.isEmpty()) {
            return;
        }
        allValidTokensByUser.forEach(token -> token.setRevoked(true));
        tokenRepo.saveAll(allValidTokensByUser);

    }

    public void saveUserToken(String token, User user) {
        Token buildToken = Token.builder()
                .token(token)
                .user(user)
                .revoked(false)
                .tokenType(TokenType.BEARER)
                .build();
        tokenRepo.save(buildToken);
    }


}
