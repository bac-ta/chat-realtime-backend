package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.enumerations.SessionStatusType;
import com.dimagesharevn.app.models.dtos.RosterDTO;
import com.dimagesharevn.app.models.dtos.SessionDTO;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import com.dimagesharevn.app.models.rests.request.UserRegistRequest;
import com.dimagesharevn.app.models.rests.response.SessionsResponse;
import com.dimagesharevn.app.models.rests.response.UserFindingResponse;
import com.dimagesharevn.app.models.rests.response.UserRegistResponse;
import com.dimagesharevn.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Value("${openfire.secret-key}")
    private String openfireSecretKey;
    @Value("${app.query.record-limit}")
    private Integer recordLimit;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
    }

    public UserRegistResponse createUser(UserRegistRequest request) throws HttpClientErrorException {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRegistRequest> requestBody = new HttpEntity<>(request, headers);
        template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/users", requestBody, UserRegistRequest.class);
        userRepository.saveBcryptedPassword(request.getUsername(), passwordEncoder.encode(request.getPassword()));
        return new UserRegistResponse(request.getUsername(), APIMessage.REGIST_USER_SUCCESSFUL);
    }

    public List<UserFindingResponse> findUser(String searchText, int start) {
        Pageable pageable = PageRequest.of(start, recordLimit);
        List<User> userList = userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchText, searchText, searchText, pageable);
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        String currentUsername = principal.getUsername();
        return userList.stream().filter(user -> !user.getUsername().equals(currentUsername)).map(user -> new UserFindingResponse(user.getUsername(), user.getEmail(), user.getName())).collect(Collectors.toList());
    }

    public Set<String> findOnlineUser() {
        //Check session exist, if ok, don't need connect, else must connect
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        String uri = APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/sessions";


        ResponseEntity<SessionsResponse> responses = template.exchange(uri, HttpMethod.GET, httpEntity,
                SessionsResponse.class);


        return responses.getBody().getSessions().stream()
                .filter(sessionDTO -> sessionDTO.getSessionStatus().equals(SessionStatusType.AUTHENTICATED.getName()))
                .map(SessionDTO::getUsername)
                .collect(Collectors.toSet());
    }

    public void addFriend(RosterRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RosterRequest> requestBody = new HttpEntity<>(request, headers);

        AccountPrincipal principal = authenticationService.getCurrentPrincipal();

        template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/users/" + principal.getUsername() + "/roster",
                requestBody, Object.class);
    }

    public RosterDTO getFriends() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        ResponseEntity<RosterDTO> responses = restTemplate.exchange(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/users/" + principal.getUsername() + "/roster",
                HttpMethod.GET, entity, RosterDTO.class);

        return responses.getBody();

    }
}
