package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.models.dtos.ChatRoomDTO;
import com.dimagesharevn.app.models.dto.HistoryDTO;
import com.dimagesharevn.app.models.entities.MessageArchive;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import com.dimagesharevn.app.repositories.MessageArchiveRepository;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    @Value("${openfire.secret-key}")
    private String openfireSecretKey;
    private final MessageArchiveRepository loadHistoryRepository;
    private final AuthenticationService authenticationService;
    @Value("${openfire.xmpp-domain}")
    private String domainName;

    @Autowired
    public ChatService(AuthenticationService authenticationService, MessageArchiveRepository loadHistoryRepository) {
        this.authenticationService = authenticationService;
        this.loadHistoryRepository = loadHistoryRepository;
    }


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

    public void addFriend(RosterRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", openfireSecretKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RosterRequest> requestBody = new HttpEntity<>(request, headers);

        AccountPrincipal principal = authenticationService.getCurrentPrincipal();

        template.postForObject(APIEndpointBase.OPENFIRE_REST_API_ENDPOINT_BASE + "/users/" + principal.getUsername() + "/roster",
                requestBody, Object.class);
    }

    public List<HistoryDTO> loadHistory(String toJID, Long sentDate) {
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        String userName = principal.getUsername();
        String fromJID = userName + "@" + domainName;
        List<MessageArchive> messageArchives;
        if (sentDate == null)
            messageArchives = loadHistoryRepository.loadHistoryFirst(fromJID, toJID);
        else
            messageArchives = loadHistoryRepository.loadHistoryNext(fromJID, toJID, sentDate);
        return messageArchives.stream().map(messageArchive -> new HistoryDTO(messageArchive.getMessageID(), messageArchive.getConversationID(), messageArchive.getSentDate(), messageArchive.getBody())).collect(Collectors.toList());
    }

}
