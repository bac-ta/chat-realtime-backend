package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    List<User> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String searchText, String searchText2, String searchText3, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE User SET bcryptedPassword=:bcryptedPassword, token=null, tokenCreateDate =null WHERE token=:token")
    void updateUserForgotInfo(String bcryptedPassword, String token);

    @Transactional
    @Modifying
    @Query("UPDATE User SET logoutTime=:logoutTime WHERE username=:username")
    void updateUserLogoutTime(Long logoutTime, String username);


}
