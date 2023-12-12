package com.example.TMS.security.jwt;

import com.example.TMS.dto.response.AuthResponse;
import com.example.TMS.dto.response.TokenValidResponse;
import com.example.TMS.exception.base.BaseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${secret.key}")
    private String SECRET_KEY;

    @Value("${app.jwtExpirationMs}")
    private Integer JWT_EXPIRED;

    @Value("${app.jwtRefreshExpirationMs}")
    private  Integer REFRESH_EXPIRED;


    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSignInKey(){
       byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
       return Keys.hmacShaKeyFor(keyBytes);
    }

    public AuthResponse generateToken(UserDetails userDetails){
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

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public TokenValidResponse validToken(String jwt){
        if(jwt.contains("Bearer ")){
            String token = jwt.substring(7);
            if(!isTokenExpired(token)){
                String email = extractEmail(token);
                return TokenValidResponse.builder()
                        .token(token)
                        .userName(email)
                        .build();
            }
            else throw new BaseException("токен истек", HttpStatus.NOT_FOUND);
        }
        else throw new BaseException("токен не найден", HttpStatus.NOT_FOUND);
    }
}
