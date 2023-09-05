package com.dadada.onecloset.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${JWT_SECRET}")
    private String SECRET;

    public String createToken(String userId) {

        long ACCESS_TOKEN_EXPIRE_TIME = 10000 * 60 * 60 * 1000L;

        return Jwts.builder()
                .setHeaderParam("alg","HS256")
                .setHeaderParam("typ","JWT")
                .claim("user_id", userId)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public Long getUserIdFromJWT(String jwt) {
        String key = Base64.getEncoder().encodeToString(SECRET.getBytes());
        return Long.parseLong(Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().get("user_id").toString());
    }

    public Long getUserIdFromHttpHeader(HttpServletRequest request) {
        String jwt = request.getHeader("Authorization").substring(7);
        return getUserIdFromJWT(jwt);
    }

    // 유효시간 검사

    //
}
