package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.factory.JwtTokenProviderFactory;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.caches.JWT;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.rests.request.LoginRequest;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.repositories.JWTRepository;
import com.dimagesharevn.app.repositories.UserRepository;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {
    private JwtTokenProviderFactory jwtFactory;
    private JWTRepository jwtRepository;
    private AuthenticationManager authManager;
    @Value("${openfire.xmpp-domain}")
    private String xmppDomain;
    @Value("${openfire.xmpp-client-connection-port}")
    private int port;
    @Value("${openfire.host}")
    private String host;

    @Autowired
    public AuthenticationService(JwtTokenProviderFactory jwtFactory, AuthenticationManager authManager, JWTRepository jwtRepository) {
        this.jwtFactory = jwtFactory;
        this.authManager = authManager;
        this.jwtRepository = jwtRepository;
    }

    public LoginResponse login(LoginRequest req) {
        String username = req.getUsername();
        String password = req.getPassword();
        try {
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(username, password)
                    .setXmppDomain(xmppDomain)
                    .setHost(host)
                    .setPort(port)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .build();

            AbstractXMPPConnection conn2 = new XMPPTCPConnection(config);
            conn2.connect().login();

        } catch (InterruptedException | XMPPException | SmackException | IOException e) {
            throw new AuthenticationCredentialsNotFoundException("");
        }
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtFactory.generateToken(authentication);
        jwtRepository.save(new JWT(jwt));
        return new LoginResponse(APIMessage.LOGIN_SUCCESSFUL, jwt);
    }

    public void logout(String jwt) {
        jwtRepository.deleteById(jwt);
    }

}
