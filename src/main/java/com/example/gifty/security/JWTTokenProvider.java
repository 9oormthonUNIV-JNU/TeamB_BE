package com.example.gifty.security;

import com.example.gifty.dto.user.UserResponseDTO;
import com.example.gifty.entity.User;
import com.example.gifty.exception.ErrorCode;
import com.example.gifty.exception.ExpiredJWTTokenException;
import com.example.gifty.exception.InvalidJWTTokenException;
import com.example.gifty.exception.UnsupportedJWTTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenProvider {

    private final Key SECRET_KEY;
    private final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 30;
    private final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 7;


    public JWTTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public UserResponseDTO.KakaoLoginDTO createToken(User user) {
        String accessToken = Jwts.builder()
                .setSubject("Token")
                .setClaims(createClaims(user))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject("Token")
                .setClaims(createClaims(user))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALID_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
        return new UserResponseDTO.KakaoLoginDTO(accessToken, refreshToken);
    }

    private Claims createClaims(User user) {
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("nickname", user.getNickanme());
        claims.put("profileImage", user.getProfileImage());
        return claims;
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new InvalidJWTTokenException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJWTTokenException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJWTTokenException(ErrorCode.UNSUPPORTED_JWT_TOKEN);
        }
    }
}
