package com.dimagesharevn.app.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NumberMessageDTO {
    private String username;
    private Long offMessageNumber;
}
