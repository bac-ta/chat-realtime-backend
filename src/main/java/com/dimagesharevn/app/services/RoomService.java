package com.dimagesharevn.app.services;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.models.dtos.ChatRoomDTO;
import com.dimagesharevn.app.models.entities.Room;
import com.dimagesharevn.app.models.rests.response.RoomResponse;
import com.dimagesharevn.app.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    @Value("${app.query.record-limit}")
    private Integer recordLimit;
    @Value("${openfire.secret-key}")
    private String openfireSecretKey;

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

    public void addUserWithRoleToChatRoom(String roomname, String userRole, String username) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatRoomDTO> requestBody = new HttpEntity<>(headers);

        template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/chatrooms/" + roomname + "/" + userRole + "/" + username,
                requestBody, Object.class);
    }
}
