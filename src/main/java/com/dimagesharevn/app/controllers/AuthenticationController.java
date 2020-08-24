package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.rest.request.LoginRequest;
import com.dimagesharevn.app.rest.response.LoginResponse;
import com.dimagesharevn.app.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bac-ta
 */
@RestController
@RequestMapping(APIEndpointBase.AUTH_ENDPOINT_BASE)
public class AuthenticationController {
    private AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse resp = authService.login(req);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
