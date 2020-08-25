package com.dimagesharevn.app.services;

import com.dimagesharevn.app.repositories.UserRepository;
import com.dimagesharevn.app.rest.request.UserRegistRequest;
import com.dimagesharevn.app.rest.response.UserRegistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserRegistResponse createUser(UserRegistRequest request){

        return null;
    }
}
