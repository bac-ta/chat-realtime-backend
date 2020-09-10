package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.caches.ShortURL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortURLRepository extends CrudRepository<ShortURL, String> {
}
