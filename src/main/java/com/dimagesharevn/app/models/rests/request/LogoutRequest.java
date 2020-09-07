package com.dimagesharevn.app.models.rests.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LogoutRequest {
    @NotBlank
    private String jwt;
}
