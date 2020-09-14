package com.dimagesharevn.app.services;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.caches.ShortenURL;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.mail.NotificationEmail;
import com.dimagesharevn.app.models.rests.request.UserRegistRequest;
import com.dimagesharevn.app.models.rests.response.UserRegistResponse;
import com.dimagesharevn.app.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sun.plugin2.message.ShowDocumentMessage;

import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWYXZabcdefghijklmnopqrstuvwyxz";
    private final MailService mailService;

    @Value("${openfire.secret-key}")
    private String openfireSecretKey;
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private Map<String, ShortenURL> shortenUrlList = new HashMap<>();

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
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

    public String forgotPassword(String email) throws MalformedURLException {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByEmail(email));

        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreateDate(LocalDateTime.now());
        String longUrl = "http://localhost:8080/user/reset-password?token=" + user.getToken();
        ShortenURL url = new ShortenURL();
        url.setFullUrl(longUrl);
        String randomChar = generateRandomString();
        setShortUrl(randomChar, url);
        user = userRepository.save(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to reset your password : " + url.getShortUrl()));
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

        userRepository.saveBcryptedPassword(user.getUsername(), passwordEncoder.encode(password));
        user.setToken(null);
        user.setTokenCreateDate(null);

        userRepository.save(user);

        return "Your password successfully updated.";
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

    private String generateRandomString() {
        StringBuilder returnValue = new StringBuilder(5);
        for (int i = 0; i < 5; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    private void setShortUrl(String randomChar, ShortenURL shortenUrl) throws MalformedURLException {
        shortenUrl.setShortUrl("http://localhost:8080/s/" + randomChar);
        shortenUrlList.put(randomChar, shortenUrl);
    }
}
