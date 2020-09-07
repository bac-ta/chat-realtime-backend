package com.dimagesharevn.app.models.caches;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash("jwt")
@Data
@AllArgsConstructor
public class JWT {
    @Id
    private String id;
}
