package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.redis.AuthTokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthTokenInfo, String> {
}
