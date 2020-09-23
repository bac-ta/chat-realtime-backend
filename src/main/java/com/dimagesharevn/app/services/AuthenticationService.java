package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.factory.JwtTokenProviderFactory;
import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.enumerations.SessionStatusType;
import com.dimagesharevn.app.models.caches.JWT;
import com.dimagesharevn.app.models.dtos.SessionDTO;
import com.dimagesharevn.app.models.rests.request.LoginRequest;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.models.rests.response.SessionsResponse;
import com.dimagesharevn.app.repositories.JWTRepository;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @Value("${openfire.secret-key}")
    private String openfireSecretKey;

    @Autowired
    public AuthenticationService(JwtTokenProviderFactory jwtFactory, AuthenticationManager authManager, JWTRepository jwtRepository) {
        this.jwtFactory = jwtFactory;
        this.authManager = authManager;
        this.jwtRepository = jwtRepository;
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
        jwtRepository.save(new JWT(jwt));

        //Check session exist, if ok, don't need connect, else must connect
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        String uri = APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/sessions/{username}";


        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("username", username);

        ResponseEntity<SessionsResponse> responses = template.exchange(uri, HttpMethod.GET, httpEntity,
                SessionsResponse.class, uriParam);

        SessionsResponse sessionsResponse = responses.getBody();
        List<SessionDTO> dtoList = sessionsResponse.getSessions();
        if (dtoList.size() > 0) {

            for (SessionDTO sessionDTO : dtoList) {
                if (sessionDTO.getSessionStatus().equals(SessionStatusType.AUTHENTICATED.name()))
                    return new LoginResponse(APIMessage.LOGIN_SUCCESSFUL, jwt);
            }
        }
        //If sessions not exist, we login
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

        return new LoginResponse(APIMessage.LOGIN_SUCCESSFUL, jwt);
    }

    public void logout(String jwt) {
        //Delete session
        String username = jwtFactory.getUsernameFromJWT(jwt);
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        String uri = APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/sessions/{username}";

        Map<String, String> uriParam = new HashMap<>();
        uriParam.put("username", username);

        template.exchange(uri, HttpMethod.DELETE, httpEntity, Object.class, uriParam);

        //delete cache
        jwtRepository.deleteById(jwt);
    }

    public Optional<JWT> findByKey(String key) {
        return jwtRepository.findById(key);
    }

    public AccountPrincipal getCurrentPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AccountPrincipal) authentication.getPrincipal();
    }

}
