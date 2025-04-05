package fooding.foodingback.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    //JWT 토큰 생성
    public String generateToken(String kakaoId) {
        return Jwts.builder()
                .setSubject(kakaoId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT 토큰 해석 (파싱)
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            System.out.println("JWT expired");
            throw e;
        } catch (JwtException e) {
            System.out.println("Invalid JWT");
            throw e;
        }
    }

    // 내부에서 사용하는 키 객체 생성
    private byte[] getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes()).getEncoded();
    }
}
