package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.MessageArchive;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageArchiveRepository extends JpaRepository<MessageArchive, String> {

    @Query(value = "SELECT * FROM ofMessageArchive as  m WHERE m.fromJID = :fromJID AND m.toJID = :toJID ORDER BY m.sentDate DESC LIMIT 10", nativeQuery = true)
    List<MessageArchive> loadHistoryFirst(@Param("fromJID") String fromJID,
                                          @Param("toJID") String toJID);

    @Query(value = "SELECT * FROM ofMessageArchive as m WHERE m.fromJID = :fromJID AND m.toJID = :toJID AND m.sentDate <= :sentDate ORDER BY m.sentDate DESC LIMIT 10", nativeQuery = true)
    List<MessageArchive> loadHistoryNext(@Param("fromJID") String fromJID,
                                         @Param("toJID") String toJID,
                                         @Param("sentDate") Long sentDate);
}
