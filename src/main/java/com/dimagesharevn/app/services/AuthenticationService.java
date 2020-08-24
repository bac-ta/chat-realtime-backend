package com.dimagesharevn.app.services;

import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.rest.request.LoginRequest;
import com.dimagesharevn.app.rest.response.LoginResponse;
import com.dimagesharevn.app.security.factory.JwtTokenProviderFactory;
import com.dimagesharevn.app.security.jwt.AccountPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private JwtTokenProviderFactory jwtFactory;
    private AuthenticationManager authManager;

    @Autowired
    public AuthenticationService(JwtTokenProviderFactory jwtFactory, AuthenticationManager authManager) {
        this.jwtFactory = jwtFactory;
        this.authManager = authManager;
    }

    public LoginResponse login(LoginRequest req) {
        String username = req.getUsername();
        String password = req.getPassword();

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtFactory.generateToken(authentication);
        return new LoginResponse(APIMessage.LOGIN_SUCCESSFUL, jwt);
    }

    public AccountPrincipal getCurrentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AccountPrincipal) authentication.getPrincipal();
    }


}
