package com.dimagesharevn.app.models.rests.request;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ResetRequest {
    @JsonProperty(required = true)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
