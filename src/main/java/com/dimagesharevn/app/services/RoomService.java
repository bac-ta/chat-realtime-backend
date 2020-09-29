package com.dimagesharevn.app.services;

import com.dimagesharevn.app.components.AppComponentFactory;
import com.dimagesharevn.app.components.OpenfireComponentFactory;
import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.models.dtos.ChatRoomDTO;
import com.dimagesharevn.app.models.entities.Room;
import com.dimagesharevn.app.models.entities.RoomMember;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import com.dimagesharevn.app.models.rests.response.RoomResponse;
import com.dimagesharevn.app.repositories.RoomMemberRepository;
import com.dimagesharevn.app.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final AppComponentFactory appFactory;
    private final OpenfireComponentFactory oFFactory;
    private final RoomMemberRepository roomMemberRepository;
    private AuthenticationService authenticationService;


    @Autowired
    public RoomService(RoomRepository roomRepository, @Qualifier("openfireComponentImpl") OpenfireComponentFactory oFFactory,
                       @Qualifier("appComponentFactoryImpl") AppComponentFactory appFactory, RoomMemberRepository roomMemberRepository,
                       AuthenticationService authenticationService) {
        this.roomRepository = roomRepository;
        this.oFFactory = oFFactory;
        this.appFactory = appFactory;
        this.roomMemberRepository = roomMemberRepository;
        this.authenticationService = authenticationService;
    }

    public List<RoomResponse> findRooms(String searchText, int start) {
        Pageable pageable = PageRequest.of(start, appFactory.getRecordLimit());
        List<Room> roomList = roomRepository.findByNameContainingIgnoreCaseOrNaturalNameContainingIgnoreCase(searchText, searchText, pageable);
        return roomList.stream().map(room -> new RoomResponse(room.getRoomID(), room.getName(), room.getNaturalName()))
                .collect(Collectors.toList());
    }

    public void addUserWithRoleToChatRoom(String roomname, String userRole, String username) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatRoomDTO> requestBody = new HttpEntity<>(headers);

        String userJid = username + "@" + oFFactory.getXmppDomain();
        template.postForObject(oFFactory.getOpenfireRestApiEndPointBase() + "/chatrooms/" + roomname + "/" + userRole + "/" + userJid,
                requestBody, Object.class);
    }

    public void createChatRoom(ChatRoomRequest request) {

        UUID roomName = UUID.randomUUID();

        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setRoomName(roomName.toString());

        Set<String> members = request.getMembers().stream().map(member -> member + "@" + oFFactory.getXmppDomain())
                .collect(Collectors.toSet());
        chatRoomDTO.setMembers(members);
        chatRoomDTO.setNaturalName(request.getNaturalName());

        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ChatRoomDTO> requestBody = new HttpEntity<>(chatRoomDTO, headers);

        template.postForObject(oFFactory.getOpenfireRestApiEndPointBase() + "/chatrooms", requestBody, ChatRoomDTO.class);
    }

    public List<RoomResponse> fetchRoomList() {
        AccountPrincipal accountPrincipal = authenticationService.getCurrentPrincipal();
        String username = accountPrincipal.getUsername();
        List<RoomMember> roomMembers = roomMemberRepository.findByJid(username + "@" + oFFactory.getXmppDomain());
        List<Long> roomIDs = roomMembers.stream().map(roomMember -> roomMember.getRoomID()).collect(Collectors.toList());
        List<Room> rooms = roomRepository.findByRoomIDIn(roomIDs);

        return rooms.stream().map(room -> new RoomResponse(room.getRoomID(), room.getName(), room.getNaturalName())).collect(Collectors.toList());
    }

}
