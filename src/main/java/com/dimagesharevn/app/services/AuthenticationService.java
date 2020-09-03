package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.factory.JwtTokenProviderFactory;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.repositories.UserRepository;
import com.dimagesharevn.app.models.rest.request.LoginRequest;
import com.dimagesharevn.app.models.rest.response.LoginResponse;
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
    @Value("${openfire.xmpp-domain}")
    private String xmppDomain;
    @Value("${openfire.xmpp-client-connection-port}")
    private int port;
    @Value("${openfire.host}")
    private String host;

    @Autowired
    public AuthenticationService(JwtTokenProviderFactory jwtFactory, UserRepository userRepository) {
        this.jwtFactory = jwtFactory;
        this.userRepository = userRepository;
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
        return new LoginResponse(APIMessage.LOGIN_SUCCESSFUL, jwt);
    }


}
