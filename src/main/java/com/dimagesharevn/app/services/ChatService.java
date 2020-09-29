package com.dimagesharevn.app.services;

import com.dimagesharevn.app.components.OpenfireComponentFactory;
import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.models.dto.HistoryDTO;
import com.dimagesharevn.app.models.dtos.ChatRoomDTO;
import com.dimagesharevn.app.models.entities.MessageArchive;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import com.dimagesharevn.app.repositories.MessageArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final MessageArchiveRepository loadHistoryRepository;
    private final AuthenticationService authenticationService;

    private final OpenfireComponentFactory oFFactory;

    @Autowired
    public ChatService(AuthenticationService authenticationService, MessageArchiveRepository loadHistoryRepository,
                       @Qualifier("openfireComponentImpl") OpenfireComponentFactory oFFactory) {
        this.authenticationService = authenticationService;
        this.loadHistoryRepository = loadHistoryRepository;
        this.oFFactory = oFFactory;
    }


    public void createChatRoom(ChatRoomRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String roomName = UUID.randomUUID().toString();

        ChatRoomDTO dto = new ChatRoomDTO();
        dto.setRoomName(roomName);
        dto.setNaturalName(request.getNaturalName());
        dto.setMembers(request.getMembers());
        HttpEntity<ChatRoomDTO> requestBody = new HttpEntity<>(dto, headers);

        template.postForObject(oFFactory.getOpenfireRestApiEndPointBase() + "/chatrooms", requestBody, ChatRoomDTO.class);
    }

    public void addFriend(RosterRequest request) {
        RestTemplate template = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", oFFactory.getSecretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RosterRequest> requestBody = new HttpEntity<>(request, headers);

        AccountPrincipal principal = authenticationService.getCurrentPrincipal();

        template.postForObject(oFFactory.getOpenfireRestApiEndPointBase() + "/users/" + principal.getUsername() + "/roster",
                requestBody, Object.class);
    }

    public List<HistoryDTO> loadHistory(String toJID, Long sentDate) {
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        String userName = principal.getUsername();
        String fromJID = userName + "@" + oFFactory.getXmppDomain();
        List<MessageArchive> messageArchives = loadHistoryRepository.loadHistory(fromJID, toJID, sentDate);

        return messageArchives.stream().map(messageArchive -> new HistoryDTO(messageArchive.getBody(), messageArchive.getSentDate())).collect(Collectors.toList());
    }

}
