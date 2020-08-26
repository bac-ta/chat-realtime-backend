package com.dimagesharevn.app.rest.request;

import com.dimagesharevn.app.constants.APIMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginRequest {
    @JsonProperty(required = true)
    @NotBlank(message = APIMessage.USER_NAME_NOT_BLANK)
    private String username;
    @JsonProperty(required = true)
    @NotBlank(message = APIMessage.PASSWORD_NOT_BLANK)
    private String password;
}
