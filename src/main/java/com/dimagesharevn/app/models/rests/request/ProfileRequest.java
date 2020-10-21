package com.dimagesharevn.app.models.rests.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {
    private String name;
    private String description;
    private String avatar;
}
