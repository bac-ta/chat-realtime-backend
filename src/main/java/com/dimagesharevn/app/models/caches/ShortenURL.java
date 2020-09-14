package com.dimagesharevn.app.models.caches;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ShortenURL {
    private String fullUrl;
    private String shortUrl;

}
