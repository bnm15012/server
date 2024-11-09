package com.dancestudio.erp.authentication;

import com.dancestudio.erp.enums.AuthType;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.auth.expiration.ms}")
    private long jwtAuthTokenExpirationTime;

    @Value("${jwt.refresh.expiration.ms}")
    private long jwtRefreshTokenExpirationTime;

    @Value("${jwt.otp.expiration.ms}")
    private long jwtOtpExpirationTime;

    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public String generateToken(String email, String otp, AuthType authType) {
        Date now = new Date();
        Date expiryDate;

        if (authType == AuthType.OTP) {
            expiryDate = new Date(now.getTime() + jwtOtpExpirationTime);
        } else {
            expiryDate = new Date(now.getTime() + jwtAuthTokenExpirationTime);
        }

        JwtBuilder tokenBuilder = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret);

        if (authType == AuthType.OTP) {
            tokenBuilder.claim("otp", otp);
        }

        return tokenBuilder.compact();
    }

    public String generateAuthToken(String email) {
        return generateToken(email, null, AuthType.AUTH);
    }

    public String generateAccessToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshTokenExpirationTime);
        return Jwts.builder()
                .setSubject(email)
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

    public Claims validateAndParseClaims(String token) throws JwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        if (claims.getExpiration().before(new Date())) {
            throw new JwtException("Token expired");
        }

        return claims;
    }

    public boolean validateRefreshToken(String token, String email) {

        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject().equals(email);
        } catch (JwtException e) {
            return false;
        }
    }
}
