package com.dimagesharevn.app.models.rests.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIErrorResponse {
    private List<String> errors;
}
