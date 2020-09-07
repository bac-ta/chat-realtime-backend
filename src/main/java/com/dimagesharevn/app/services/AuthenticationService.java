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
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {
    private JwtTokenProviderFactory jwtFactory;
    private UserRepository userRepository;
    private JWTRepository jwtRepository;
    @Value("${openfire.xmpp-domain}")
    private String xmppDomain;
    @Value("${openfire.xmpp-client-connection-port}")
    private int port;
    @Value("${openfire.host}")
    private String host;

    @Autowired
    public AuthenticationService(JwtTokenProviderFactory jwtFactory, UserRepository userRepository, JWTRepository jwtRepository) {
        this.jwtFactory = jwtFactory;
        this.userRepository = userRepository;
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
            return new LoginResponse(APIMessage.LOGIN_FAILURE, null);
        }
        User user = userRepository.findByUsername(username).get();
        String jwt = jwtFactory.generateToken(user.getUsername(), user.getEmail(), user.getName());
        jwtRepository.save(new JWT(jwt));
        return new LoginResponse(APIMessage.LOGIN_SUCCESSFUL, jwt);
    }

    public void logout(String jwt) {
        jwtRepository.deleteById(jwt);
    }

}
