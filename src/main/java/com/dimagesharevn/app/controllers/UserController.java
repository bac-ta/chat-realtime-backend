package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.caches.ShortenURL;
import com.dimagesharevn.app.models.dtos.RosterDTO;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.mail.NotificationEmail;
import com.dimagesharevn.app.models.rests.request.NewPasswordRequest;
import com.dimagesharevn.app.models.rests.request.ResetRequest;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import com.dimagesharevn.app.models.rests.request.UserRegistRequest;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.models.rests.response.SessionsResponse;
import com.dimagesharevn.app.models.rests.response.UserRegistResponse;
import com.dimagesharevn.app.repositories.UserRepository;
import com.dimagesharevn.app.services.MailService;
import com.dimagesharevn.app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(APIEndpointBase.USER_ENDPOINT_BASE)
@Api(
        tags = "User API"
)
public class UserController {
    private final UserService userService;
    private final MailService mailService;
    private final UserRepository userRepository;

    private Map<String, ShortenURL> shortenUrlList = new HashMap<>();

    @Autowired
    public UserController(UserService userService, MailService mailService, UserRepository userRepository) {
        this.userService = userService;
        this.mailService = mailService;
        this.userRepository = userRepository;
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
        if (StringUtils.isBlank(response.getUsername())) return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "User api", notes = "Find online user", response = SessionsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = ""),
    })
    @GetMapping("/online")
    public ResponseEntity<Set<String>> findOnlineUser() {
        return new ResponseEntity<>(userService.findOnlineUser(), HttpStatus.OK);
    }

    @ApiOperation(value = "Add friend", notes = "Add friend API")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 409, message = "")
    })

    @PostMapping("/addFriend/{username}")
    public ResponseEntity<Void> addFriend(@PathVariable("username") String username) {
        RosterRequest rosterRequest = new RosterRequest(username);
        userService.addFriend(rosterRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get friends", notes = "Get friends API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = RosterDTO.class),
            @ApiResponse(code = 400, message = "")
    })

    @GetMapping("/getFriends")
    public ResponseEntity<RosterDTO> getFriends() {
        RosterDTO rosterDTO = userService.getFriends();
        return new ResponseEntity<>(rosterDTO, HttpStatus.OK);
    }


    @GetMapping(value = "/s/{randomstring}")
    public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException {
        response.sendRedirect(shortenUrlList.get(randomString).getFullUrl());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ResetRequest resetRequest) throws MalformedURLException {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByEmail(resetRequest.getEmail()));
        String response = userService.forgotPassword(userOptional);
        User user = userOptional.get();

        String longUrl = "http://207.148.119.90:1234/api/user/reset-password?token=" + user.getToken();
        ShortenURL url = new ShortenURL();
        url.setFullUrl(longUrl);
        String randomChar = getRandomChars();
        setShortUrl(randomChar, url);

        mailService.sendMail(new NotificationEmail("Forgotten password reset",
                user.getEmail(), "Somebody (hopefully you) request a new password for the " +
                "Dimageshare Chat App account for " +
                user.getEmail() + ". No changes have been made to your account yet.\n\n" +
                "You can reset your password by clicking the link below: " + url.getShortUrl()
                + "\nIf you did not request a new password, please let tell us know " +
                "immediately by replying to this email."
                + "\n\n\nYours," + "\nThe Dimageshare team"));


        if (!response.startsWith("Invalid")) {
            response = "http://207.148.119.90:1234/api/user/reset-password?token=" + response;
        }
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping("/reset-password")
    public void showChangePasswordPage(HttpServletResponse response, @RequestParam String token) throws IOException {
        String result = userService.validateToken(token);
        if (result != null) {
            response.sendRedirect("http://207.148.119.90:5678/404");
        } else {
            response.sendRedirect("http://207.148.119.90:5678/pre-auth/new-password?token=" + token);
        }
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestBody NewPasswordRequest request) {

        return userService.resetPassword(request.getResetToken(), request.getPassword());
    }


    private void setShortUrl(String randomChar, ShortenURL shortenUrl) {
        shortenUrl.setShortUrl("http://207.148.119.90:1234/api/user/s/" + randomChar);
        shortenUrlList.put(randomChar, shortenUrl);
    }

    private String getRandomChars() {
        String randomStr = "";
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 5; i++)
            randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
        return randomStr;
    }
}
