package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUser, String> {
    List<GroupUser> findByUsername(String username);
}
