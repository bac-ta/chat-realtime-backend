package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.request.UserRegistRequest;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.models.rests.response.UserRegistResponse;
import com.dimagesharevn.app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.MalformedURLException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(APIEndpointBase.USER_ENDPOINT_BASE)
@Api(
        tags = "User API"
)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "User api", notes = "Create user", response = LoginResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = APIMessage.REGIST_USER_SUCCESSFUL),
            @ApiResponse(code = 400, message = APIMessage.REGIST_USER_FAIL),
    })
    @PostMapping("/create")
    public ResponseEntity<UserRegistResponse> createUser(@Valid @RequestBody UserRegistRequest request) {
        UserRegistResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MalformedURLException {

        String response = userService.forgotPassword(email);

        if (!response.startsWith("Invalid")) {
            response = "http://localhost:8080/user/reset-password?token=" + response;
        }
        return new ResponseEntity<>(response, OK);
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password) {

        return userService.resetPassword(token, password);
    }
}
