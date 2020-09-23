package com.dimagesharevn.app.models.rests.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSaveRequest {
    @NotBlank
    @Size(max = 50)
    private String name;
    @Size(max = 255)
    private String description;
}
