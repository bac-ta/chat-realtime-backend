package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.dto.HistoryDTO;
import com.dimagesharevn.app.models.dtos.NumberMessageDTO;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import com.dimagesharevn.app.repositories.MessageArchiveRepository;
import com.dimagesharevn.app.repositories.UserRepository;
import com.dimagesharevn.app.services.AuthenticationService;
import com.dimagesharevn.app.services.ChatService;
import com.dimagesharevn.app.services.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIEndpointBase.CHAT_ENPOINT_BASE)
@Api(
        tags = "Chat API"
)
public class ChatController {
    private final ChatService chatService;
    private final MessageArchiveRepository messageArchiveRepository;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    public ChatController(ChatService chatService,MessageArchiveRepository messageArchiveRepository
            ,AuthenticationService authenticationService,UserRepository userRepository) {
        this.chatService = chatService;
        this.messageArchiveRepository = messageArchiveRepository;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
    }

    @ApiOperation(value = "Create a chat room API", notes = "Create chat")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = APIMessage.CREATE_CHAT_ROOM_SUCCESSFUL),
            @ApiResponse(code = 400, message = APIMessage.CREATE_CHAT_ROOM_FAILURE)
    })
    @PostMapping("/create")
    public ResponseEntity<Void> createChatRoom(ChatRoomRequest request) {
        chatService.createChatRoom(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Add friend", notes = "Add friend API")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 409, message = "")
    })

    @PostMapping("/addFriend/{username}")
    public ResponseEntity<Void> addFriend(@PathVariable("username") String username) {
        RosterRequest rosterRequest = new RosterRequest(username);
        chatService.addFriend(rosterRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Load history API", notes = "Load chat history")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = ""),
    })
    @GetMapping("/loadHistory")
    public ResponseEntity<List<HistoryDTO>> findBody(@Param("toJID") String toJID,
                                                     @Param("sentDate") Long sentDate) {
        List<HistoryDTO> historyDTOS = chatService.loadHistory(toJID, sentDate);
        return new ResponseEntity<>(historyDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/numOfMessOff")
    @ResponseBody
    public List<NumberMessageDTO> getAllMess(){
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        String username = principal.getUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.get();

        List<NumberMessageDTO> messageArchives= messageArchiveRepository.findFromJIDCountMessage(user.getLogoutTime(),user.getLoginTime());
        return messageArchives;

    }

}
