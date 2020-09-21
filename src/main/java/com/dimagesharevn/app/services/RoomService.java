package com.dimagesharevn.app.services;

import com.dimagesharevn.app.models.entities.Room;
import com.dimagesharevn.app.models.rests.response.RoomResponse;
import com.dimagesharevn.app.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    @Value("${app.query.record-limit}")
    private Integer recordLimit;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomResponse> findRooms(String searchText, int start) {
        Pageable pageable = PageRequest.of(start, recordLimit);
        List<Room> roomList = roomRepository.findByNameContainingIgnoreCaseOrNaturalNameContainingIgnoreCase(searchText, searchText, pageable);
        return roomList.stream().map(room -> new RoomResponse(room.getName(), room.getNaturalName(), room.getDescription()))
                .collect(Collectors.toList());
    }
}
