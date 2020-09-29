package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.MessageArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageArchiveRepository extends JpaRepository<MessageArchive, String>, JpaSpecificationExecutor<MessageArchive> {
}
