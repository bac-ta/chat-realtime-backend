package com.dimagesharevn.app.services;

import com.dimagesharevn.app.components.AppComponentFactory;
import com.dimagesharevn.app.components.OpenfireComponentFactory;
import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.dtos.RosterDTO;
import com.dimagesharevn.app.models.dtos.SessionDTO;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.rests.request.PasswordResetRequest;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import com.dimagesharevn.app.models.rests.request.UserRegistRequest;
import com.dimagesharevn.app.models.rests.response.SessionsResponse;
import com.dimagesharevn.app.models.rests.response.UserFindingResponse;
import com.dimagesharevn.app.models.rests.response.UserRegistResponse;
import com.dimagesharevn.app.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;
    private final OpenfireComponentFactory oFFactory;
    private final AppComponentFactory appFactory;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                       AuthenticationService authenticationService, @Qualifier("openfireComponentImpl") OpenfireComponentFactory oFFactory,
                       @Qualifier("appComponentFactoryImpl") AppComponentFactory appFactory) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.oFFactory = oFFactory;
        this.appFactory = appFactory;
    }

    public UserRegistResponse createUser(UserRegistRequest request) throws HttpClientErrorException {
        RestTemplate template = new RestTemplate();
        String email = request.getEmail();
        if (userRepository.findByEmail(email) != null)
            return new UserRegistResponse(null, APIMessage.REGIST_USER_FAIL_EMAIL);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRegistRequest> requestBody = new HttpEntity<>(request, headers);
        template.postForObject(oFFactory.getOpenfireRestApiEndPointBase() + "/users", requestBody, UserRegistRequest.class);
        userRepository.saveBcryptedPassword(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        return new UserRegistResponse(request.getUsername(), APIMessage.REGIST_USER_SUCCESSFUL);
    }

    public List<UserFindingResponse> findUser(String searchText, int start) {
        Pageable pageable = PageRequest.of(start, appFactory.getRecordLimit());
        List<User> userList = userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchText, searchText, searchText, pageable);
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        String currentUsername = principal.getUsername();
        return userList.stream().filter(user -> !user.getUsername().equals(currentUsername)).map(user -> new UserFindingResponse(user.getUsername(), user.getEmail(), user.getName())).collect(Collectors.toList());
    }

    public Set<String> findOnlineUser() {
        //Check session exist, if ok, don't need connect, else must connect
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        String uri = oFFactory.getOpenfireRestApiEndPointBase() + "/sessions";


        ResponseEntity<SessionsResponse> responses = template.exchange(uri, HttpMethod.GET, httpEntity,
                SessionsResponse.class);

        return responses.getBody().getSessions().stream()
                .filter(sessionDTO -> !StringUtils.isBlank(sessionDTO.getHostAddress()))
                .map(SessionDTO::getUsername)
                .collect(Collectors.toSet());
    }

    public void addFriend(RosterRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RosterRequest> requestBody = new HttpEntity<>(request, headers);

        AccountPrincipal principal = authenticationService.getCurrentPrincipal();

        template.postForObject(oFFactory.getOpenfireRestApiEndPointBase() + "/users/" + principal.getUsername() + "/roster",
                requestBody, Object.class);
    }

    public RosterDTO getFriends() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        ResponseEntity<RosterDTO> responses = restTemplate.exchange(oFFactory.getOpenfireRestApiEndPointBase() + "/users/" + principal.getUsername() + "/roster",
                HttpMethod.GET, entity, RosterDTO.class);

        return responses.getBody();

    }


    public String forgotPassword(Optional<User> userOptional) {


        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreateDate(LocalDateTime.now());

        user = userRepository.save(user);

        return user.getToken();
    }

    public String resetPassword(String token, String password) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByToken(token));

        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreateDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }

        User user = userOptional.get();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        PasswordResetRequest resetRequest = new PasswordResetRequest(password);
        HttpEntity<PasswordResetRequest> entity = new HttpEntity<>(resetRequest, headers);
        restTemplate.exchange(oFFactory.getOpenfireRestApiEndPointBase() + "/users/" + user.getUsername(),
                HttpMethod.PUT, entity, PasswordResetRequest.class);

        userRepository.updateUserForgotInfo(passwordEncoder.encode(password), token);

        return "Your password successfully updated.";
    }

    public String validateToken(String token) {
        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByToken(token));
        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreateDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";
        }
        return null;
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    //check token expire
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
}
