package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    User findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE User SET bcryptedPassword=:bcryptedPassword WHERE username=:username")
    void saveBcryptedPassword(String username, String bcryptedPassword);
}
