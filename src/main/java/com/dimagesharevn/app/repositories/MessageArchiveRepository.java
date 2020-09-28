package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.dtos.NumberMessageDTO;
import com.dimagesharevn.app.models.entities.MessageArchive;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageArchiveRepository extends JpaRepository<MessageArchive, String> {

   @Query("SELECT m FROM MessageArchive m WHERE m.fromJID = ?1 AND m.toJID = ?2 AND m.sentDate <= ?3 ORDER BY m.sentDate DESC  ")
    List<MessageArchive> loadHistory(String fromJID, String toJID, Long sentDate);

    @Query(
            "SELECT " +
            "   m.fromJID , COUNT(m.fromJID) " +
            "FROM " +
            "    MessageArchive m " +
            "WHERE "+
            "  m.sentDate "+
            "BETWEEN :logoutTime AND :latestLogin "+
            "GROUP BY " +
            "    m.fromJID")
    List<NumberMessageDTO> findFromJIDCountMessage(@Param("logoutTime")Long logoutTime, @Param("latestLogin")Long latestLogin);
}
