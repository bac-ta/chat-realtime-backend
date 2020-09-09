package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.caches.JWT;
import org.springframework.data.repository.CrudRepository;

public interface JWTRepository extends CrudRepository<JWT, String> {
}
