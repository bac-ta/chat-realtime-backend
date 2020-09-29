package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.dtos.NumberMessageDTO;
import com.dimagesharevn.app.models.entities.MessageArchive;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageArchiveRepository extends JpaRepository<MessageArchive, String>, JpaSpecificationExecutor<MessageArchive> {
    @Query(
            "SELECT " +
                    "   new com.dimagesharevn.app.models.dtos.NumberMessageDTO(m.fromJID, COUNT(m.fromJID)) " +
                    "FROM " +
                    "    MessageArchive m " +
                    "WHERE "+
                    "  m.sentDate "+
                    "BETWEEN :logoutTime AND :latestLogin "+
                    "GROUP BY " +
                    "    m.fromJID")
    List<NumberMessageDTO> findFromJIDCountMessage(@Param("logoutTime")Long logoutTime, @Param("latestLogin")Long latestLogin);
}
