package com.dimagesharevn.app.rest.request;

import com.dimagesharevn.app.constants.APIMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequest {
    @JsonProperty(value = "username", required = true)
    @NotBlank(message = APIMessage.USER_NAME_NOT_BLANK)
    private String username;
    @JsonProperty(required = true)
    @NotBlank(message = APIMessage.PASSWORD_NOT_BLANK)
    private String password;
}
