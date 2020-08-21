package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
