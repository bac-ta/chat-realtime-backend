package com.dimagesharevn.app.services;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.models.dto.ChatRoomDTO;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {
    @Value("${openfire.secret-key}")
    private String openfireSecretKey;

    public void createChatRoom(ChatRoomRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setRoomName(request.getRoomName());
        dto.setNaturalName(request.getNaturalName());
        dto.setDescription(request.getDescription());
        dto.setMembers(request.getMembers());
        HttpEntity<ChatRoomDTO> requestBody = new HttpEntity<>(dto, headers);

        template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/chatrooms", requestBody, ChatRoomDTO.class);
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
