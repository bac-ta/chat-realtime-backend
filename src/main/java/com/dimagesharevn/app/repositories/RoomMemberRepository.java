package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {
    List<RoomMember> findByJid(String jid);
}
