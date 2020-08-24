package com.dimagesharevn.app.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    @JsonProperty(value = "access_token")
    private String accessToken;
}
