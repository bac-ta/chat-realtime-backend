package com.dimagesharevn.app.services;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.repositories.UserRepository;
import com.dimagesharevn.app.rest.request.UserRegistRequest;
import com.dimagesharevn.app.rest.response.UserRegistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRegistResponse createUser(UserRegistRequest request) {
        RestTemplate template = new RestTemplate();
        UserRegistRequest created = template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/plugins/restapi/v1/users", request, UserRegistRequest.class);
        if (created != null)
            return new UserRegistResponse(created.getUsername(), APIMessage.REGIST_USER_SUCCESSFUL);

        return new UserRegistResponse(null, APIMessage.REGIST_USER_FAIL);
    }
}
