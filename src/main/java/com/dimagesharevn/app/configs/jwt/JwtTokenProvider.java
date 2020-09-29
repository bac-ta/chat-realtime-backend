package com.dimagesharevn.app.configs.jwt;

import com.dimagesharevn.app.components.AppComponentFactory;
import com.dimagesharevn.app.configs.factory.JwtTokenProviderFactory;
import com.dimagesharevn.app.constants.ExceptionMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bac-ta
 */
@Component("jwtTokenProvider")
public class JwtTokenProvider implements JwtTokenProviderFactory {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Qualifier("appComponentFactoryImpl")
    @Autowired
    private AppComponentFactory appFactory;

    @Override
    public String generateToken(Authentication authentication) {
        AccountPrincipal principal = (AccountPrincipal) authentication.getPrincipal();
        Date dateNow = new Date();
        Date expiryDate = new Date(dateNow.getTime() + appFactory.getTokenExpirationMsec());

        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("username", principal.getUsername());
        claimMap.put("email", principal.getEmail());
        claimMap.put("authorities", principal.getAuthorities());
        claimMap.put("name", principal.getName());

        return Jwts.builder().setClaims(claimMap).setIssuedAt(dateNow).
                setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, appFactory.getTokenSecret()).compact();
    }

    @Override
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(appFactory.getTokenSecret()).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    @Override
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appFactory.getTokenSecret()).parseClaimsJws(authToken);
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
