package com.dimagesharevn.app.models.rests.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistResponse {
    private String username;
    private String message;
}
