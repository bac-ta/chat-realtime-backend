package com.dimagesharevn.app.models.caches;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash(value = "short_url", timeToLive = 1800)
@Data
@AllArgsConstructor
public class ShortURL {
    @Id
    private String id;
    private String token;
}
