package com.dimagesharevn.app.models.rests.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewPasswordRequest {
    @JsonProperty
    private String resetToken;

    @JsonProperty(required = true)
    private String password;

}
