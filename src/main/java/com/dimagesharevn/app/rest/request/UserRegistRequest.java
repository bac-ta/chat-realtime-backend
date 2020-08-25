package com.dimagesharevn.app.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistRequest {
    private String username;
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
