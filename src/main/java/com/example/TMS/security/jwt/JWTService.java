package com.example.TMS.security.jwt;

import com.example.TMS.dto.response.AuthResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**
 * для создания, извлечения и проверки JWT
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JWTService {

    @Value("${secret.key}")
    String SECRET_KEY;

    @Value("${app.jwtExpirationMs}")
    Integer JWT_EXPIRED;

    @Value("${app.jwtRefreshExpirationMs}")
    Integer REFRESH_EXPIRED;


    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthResponse generateToken(UserDetails userDetails) {
        Date dateExpiredToken = new Date(System.currentTimeMillis() + JWT_EXPIRED);
        Date dateExpiredRefreshToken = new Date(System.currentTimeMillis() + REFRESH_EXPIRED);

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userDetails.getAuthorities().iterator().next().getAuthority());

        return AuthResponse.builder()
                .jwt(Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(dateExpiredToken)
                        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                        .compact()
                )
                .refreshToken(Jwts.builder()
                        .setClaims(new HashMap<>())
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(dateExpiredRefreshToken)
                        .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                        .compact()
                )
                .dateExpiredAccessToken(dateExpiredToken)
                .dateExpiredRefreshToken(dateExpiredRefreshToken)
                .build();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
