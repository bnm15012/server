package com.dancestudio.erp.authentication;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class JwtOtpUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private static final long OTP_EXPIRATION_MS = 300_000;

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public String generateOtpToken(String email, String otp) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + OTP_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(email)
                .claim("otp", otp)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateOtpToken(String token, String otp) {
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            String tokenOtp = claims.get("otp", String.class);
            return otp.equals(tokenOtp) && !claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return false;
        }
    }
}
