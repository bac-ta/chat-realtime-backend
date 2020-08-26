package com.dimagesharevn.app.config.jwt;

import com.dimagesharevn.app.config.factory.JwtTokenProviderFactory;
import com.dimagesharevn.app.constants.ExceptionMessage;
import com.dimagesharevn.app.enumerations.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bac-ta
 */
@Component
public class JwtTokenProvider implements JwtTokenProviderFactory {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${app.auth.token-secret}")
    private String clientSecrectKey;
    @Value("${app.auth.token-expiration-msec}")
    private int expirationInMs;

    @Override
    public String generateToken(String username, String name, String email) {
        Date dateNow = new Date();
        Date expiryDate = new Date(dateNow.getTime() + expirationInMs);

        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("username", username);
        claimMap.put("email", email);
        claimMap.put("name", name);
        claimMap.put("authority", UserType.MEMBER.name());

        return Jwts.builder().setClaims(claimMap).setIssuedAt(dateNow).
                setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, clientSecrectKey).compact();
    }

    @Override
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(clientSecrectKey).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    @Override
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(clientSecrectKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error(ExceptionMessage.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException | IllegalArgumentException ex) {
            logger.error(ExceptionMessage.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException ex) {
            logger.error(ExceptionMessage.EXPIRED_JWT_TOKEN);
        } catch (UnsupportedJwtException ex) {
            logger.error(ExceptionMessage.UNSUPPORT_JWT_TOKEN);
        }
        return false;
    }
}
