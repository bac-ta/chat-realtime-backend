package com.dimagesharevn.app.models.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("AuthTokenInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenInfo implements Serializable {
    private String username;
    private String jwtToken;
}
