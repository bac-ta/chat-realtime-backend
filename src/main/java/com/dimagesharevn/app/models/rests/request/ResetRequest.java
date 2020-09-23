package com.dimagesharevn.app.models.rests.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetRequest {
    @JsonProperty(required = true)
    private String email;

}
