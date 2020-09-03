package com.dimagesharevn.app.services;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rest.request.UserRegistRequest;
import com.dimagesharevn.app.models.rest.response.UserRegistResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Value("${openfire.secret-key}")
    private String openfireSecretKey;

    public UserRegistResponse createUser(UserRegistRequest request) throws HttpClientErrorException {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRegistRequest> requestBody = new HttpEntity<>(request, headers);
        try {
            template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/users", requestBody, UserRegistRequest.class);
        } catch (HttpClientErrorException e) {
            return new UserRegistResponse(null, APIMessage.REGIST_USER_FAIL);
        }
        return new UserRegistResponse(request.getUsername(), APIMessage.REGIST_USER_SUCCESSFUL);
    }
}
