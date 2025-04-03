package com.dancestudio.erp.authentication;

import com.dancestudio.erp.enums.AuthType;
import com.dancestudio.erp.exception.MembershipExpiredException;
import com.dancestudio.erp.util.DateUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Objects;
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

    public String generateToken(String email, Date membershipEndDate, String otp, AuthType authType) {
        Date now = DateUtil.getCurrentDateUTC();
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
                .signWith(getSignKey(), SignatureAlgorithm.HS256);

        if (authType == AuthType.OTP) {
            tokenBuilder.claim("otp", otp);
        }

        if(Objects.nonNull(membershipEndDate)) {
            tokenBuilder.claim("membershipEndDate", membershipEndDate);
        }

        return tokenBuilder.compact();
    }

    public String generateAuthToken(String email, Date membershipEndDate) {
        return generateToken(email, membershipEndDate, null, AuthType.AUTH);
    }

    public String generateAccessToken(String email) {
        Date now = DateUtil.getCurrentDateUTC();
        Date expiryDate = new Date(now.getTime() + jwtRefreshTokenExpirationTime);
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateOtpToken(String token, String otp) {
        try {
            Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            String tokenOtp = claims.get("otp", String.class);
            return otp.equals(tokenOtp) && !claims.getExpiration().before(DateUtil.getCurrentDateUTC());
        } catch (JwtException e) {
            return false;
        }
    }

    public Claims validateAndParseClaims(String token) throws JwtException {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        if (claims.getExpiration().before(DateUtil.getCurrentDateUTC())) {
            throw new JwtException("Token expired");
        }

        Date membershipEndDate = claims.get("membershipEndDate", Date.class);
        if (membershipEndDate != null && membershipEndDate.before(DateUtil.getCurrentDateUTC())) {
            throw new MembershipExpiredException("Membership is expired");
        }

        return claims;
    }

    public boolean validateRefreshToken(String token, String email) {

        try {
            Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
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
