package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.Group;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
    List<Group> findByGroupNameContainingIgnoreCase(String searchText, Pageable pageable);
}
