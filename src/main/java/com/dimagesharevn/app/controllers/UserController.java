package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.caches.ShortenURL;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.mail.NotificationEmail;
import com.dimagesharevn.app.models.rests.request.UserRegistRequest;
import com.dimagesharevn.app.models.rests.response.LoginResponse;
import com.dimagesharevn.app.models.rests.response.UserRegistResponse;
import com.dimagesharevn.app.repositories.UserRepository;
import com.dimagesharevn.app.services.MailService;
import com.dimagesharevn.app.services.UserService;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.net.MalformedURLException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    })
    @PostMapping("/create")
    public ResponseEntity<UserRegistResponse> createUser(@Valid @RequestBody UserRegistRequest request) {
        UserRegistResponse response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/s/{randomstring}")
    public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException {
        response.sendRedirect(shortenUrlList.get(randomString).getFullUrl());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) throws MalformedURLException {
        String response = userService.forgotPassword(email);

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByEmail(email));
        User user = userOptional.get();

        String longUrl = "http://localhost:8080/api/user/reset-password?token=" + user.getToken();
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
            response = "http://localhost:8080/api/user/reset-password?token=" + response;
        }
        return new ResponseEntity<>(response, OK);
    }

    @GetMapping("/reset-password")
    public void showChangePasswordPage(HttpServletResponse response, @RequestParam String token) throws IOException {
        String result = userService.validateToken(token);
        if (result != null) {
            response.sendRedirect("https://www.google.com/");
        }else{
            response.sendRedirect("http://localhost:4200/pre-auth/new-password?token="+token);
        }
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password) {

        return userService.resetPassword(token, password);
    }


    private void setShortUrl(String randomChar, ShortenURL shortenUrl) {
        shortenUrl.setShortUrl("http://localhost:8080/api/user/s/" + randomChar);
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
