package com.dimagesharevn.app.repositories;

import com.dimagesharevn.app.models.entities.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, String> {
    List<Room> findByNameContainingIgnoreCaseOrNaturalNameContainingIgnoreCase(String searchText, String searchText2, Pageable pageable);

    List<Room> findByRoomIDIn(List<Long> roomIDS);
}
