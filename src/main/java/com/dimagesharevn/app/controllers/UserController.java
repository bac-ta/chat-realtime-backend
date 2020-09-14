package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.request.UserRegistRequest;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.models.rests.response.SessionsResponse;
import com.dimagesharevn.app.models.rests.response.UserFindingResponse;
import com.dimagesharevn.app.models.rests.response.UserRegistResponse;
import com.dimagesharevn.app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
            @ApiResponse(code = 409, message = APIMessage.REGIST_USER_CONFLICT)
    })
    @PostMapping("/create")
    public ResponseEntity<UserRegistResponse> createUser(@Valid @RequestBody UserRegistRequest request) {
        UserRegistResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "User api", notes = "Search user", response = SessionsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = ""),
    })
    @GetMapping("/search")
    public ResponseEntity<List<UserFindingResponse>> findUser(@RequestParam("text-search") String textSearch) {
        return new ResponseEntity<>(userService.findUser(textSearch), HttpStatus.OK);
    }

    @ApiOperation(value = "User api", notes = "Find online user", response = SessionsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = ""),
    })
    @GetMapping("/online")
    public ResponseEntity<SessionsResponse> findOnlineUser() {
        return new ResponseEntity<>(userService.findOnlineUser(), HttpStatus.OK);
    }
}
