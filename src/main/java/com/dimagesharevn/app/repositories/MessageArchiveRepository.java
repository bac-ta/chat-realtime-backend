package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.dtos.NumberMessageDTO;
import com.dimagesharevn.app.models.entities.MessageArchive;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageArchiveRepository extends JpaRepository<MessageArchive, String>, JpaSpecificationExecutor<MessageArchive> {
    @Query(
            "SELECT " +
                    "   new com.dimagesharevn.app.models.dtos.NumberMessageDTO(m.fromJID, COUNT(m.fromJID)) " +
                    "FROM " +
                    "    MessageArchive m " +
                    "WHERE "+
                    "m.toJID = :toJID" +
                    " AND " +
                    "  m.sentDate "+
                    "BETWEEN :logoutTime AND unix_timestamp()*1000 " +
                    "GROUP BY " +
                    "    m.fromJID")
    List<NumberMessageDTO> findFromJIDCountMessage(@Param("logoutTime")Long logoutTime,
//                                                   @Param("latestLogin")Long latestLogin,
                                                   @Param("toJID")String toJID);

//    @Modifying
//    @Query(value ="select m.fromJID, COUNT(m.fromJID) from MessageArchive m " +
//            "where m.toJID = :toJID AND m.sentDate between :logoutTime AND unix_timestamp(now()) group by m.fromJID"
//            ,nativeQuery = true)
//    List<NumberMessageDTO> findFromJIDCountMessage1(@Param("logoutTime")Long logoutTime,
////                                                   @Param("latestLogin")Long latestLogin,
//                                                    @Param("toJID")String toJID);
}
