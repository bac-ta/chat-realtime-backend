package com.dimagesharevn.app.models.rest.request;

import com.dimagesharevn.app.constants.APIMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistRequest {
    @NotBlank(message = APIMessage.USER_NAME_NOT_BLANK)
    private String username;
    private String name;
    private String email;
    @NotBlank
    private String password;
}
