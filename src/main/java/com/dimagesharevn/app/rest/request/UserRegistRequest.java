package com.dimagesharevn.app.rest.request;

import com.dimagesharevn.app.annonations.FieldMatch;
import com.dimagesharevn.app.constants.APIMessage;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = APIMessage.PASSWORD_NOT_MATCH)
})
@Data
@AllArgsConstructor
public class UserRegistRequest {
    @NotBlank(message = APIMessage.USER_NAME_NOT_BLANK)
    private String username;
    private String name;
    private String email;
    @NotBlank
    private String password;
    private String confirmPassword;
}
