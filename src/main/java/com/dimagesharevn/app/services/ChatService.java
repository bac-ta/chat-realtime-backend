package com.dimagesharevn.app.services;

import com.dimagesharevn.app.components.OpenfireComponentFactory;
import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.models.dto.HistoryDTO;
import com.dimagesharevn.app.models.dtos.ChatRoomDTO;
import com.dimagesharevn.app.models.dtos.NumberMessageDTO;
import com.dimagesharevn.app.models.entities.MessageArchive;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import com.dimagesharevn.app.repositories.MessageArchiveRepository;
import com.dimagesharevn.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dimagesharevn.app.models.specification.MessageSpecification.hasFromJID;
import static com.dimagesharevn.app.models.specification.MessageSpecification.hasSentDate;
import static com.dimagesharevn.app.models.specification.MessageSpecification.hasToJID;

@Service
public class ChatService {
    private final MessageArchiveRepository loadHistoryRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final OpenfireComponentFactory oFFactory;
    private final MessageArchiveRepository archiveRepository;

    @Autowired
    public ChatService(AuthenticationService authenticationService, MessageArchiveRepository loadHistoryRepository,
                       @Qualifier("openfireComponentImpl") OpenfireComponentFactory oFFactory,
                       UserRepository userRepository, MessageArchiveRepository archiveRepository) {
        this.authenticationService = authenticationService;
        this.loadHistoryRepository = loadHistoryRepository;
        this.oFFactory = oFFactory;
        this.userRepository = userRepository;
        this.archiveRepository = archiveRepository;
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

    public List<HistoryDTO> loadHistory(String userNameTo, Long sentDate) {
        Pageable limit = PageRequest.of(0, 10);
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        String userName = principal.getUsername();
        String fromJID = userName + "@" + oFFactory.getXmppDomain();
        Specification conditions = Specification.where(hasFromJID(fromJID))
                .and(hasToJID(userNameTo + "@" + oFFactory.getXmppDomain()));
        Specification conditionsSentDate = Specification.where(hasFromJID(fromJID))
                .and(hasToJID(userNameTo + "@" + oFFactory.getXmppDomain()))
                .and(hasSentDate(sentDate));
        Page<MessageArchive> messageArchives = loadHistoryRepository.findAll(sentDate == null ? conditions : conditionsSentDate, limit);
        return messageArchives.stream().map(messageArchive -> new HistoryDTO(messageArchive.getMessageID(), messageArchive.getConversationID(), messageArchive.getSentDate(), messageArchive.getBody())).collect(Collectors.toList());
    }

    public List<NumberMessageDTO> loadNumMessOff() {
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        String username = principal.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.get();
        String toJID = username + "@" + oFFactory.getXmppDomain();
        long localTime = System.currentTimeMillis();
//        List<NumberMessageDTO> messageArchives = archiveRepository.findFromJIDCountMessage(user.getLogoutTime(),
//                localTime, toJID);
        List<NumberMessageDTO> messageArchives = archiveRepository.findFromJIDCountMessage(user.getLogoutTime(),toJID);

        for(NumberMessageDTO messageDTO: messageArchives){
            String[] list = messageDTO.getUsername().split("@");
            messageDTO.setUsername(list[0]);
        }
        return messageArchives;
    }

}
