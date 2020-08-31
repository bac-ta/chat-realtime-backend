package com.dimagesharevn.app.config.factory;

import org.springframework.stereotype.Service;

/**
 * @author bac-ta
 */
@Service
public interface JwtTokenProviderFactory {
    String generateToken(String username, String email, String name);

    String getUsernameFromJWT(String token);

    boolean validateToken(String authToken);
}
