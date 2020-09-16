package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import com.dimagesharevn.app.services.ChatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIEndpointBase.CHAT_ENPOINT_BASE)
@Api(
        tags = "Chat API"
)
public class ChatController {
    private ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
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

    @ApiOperation(value = "Add uset with role to chat room API", notes = "Add uset with role to chat room api")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @PostMapping("/addUser/{roomname}/{userRole}/{username}")
    public ResponseEntity<Void> addUserWithRoleToChatRoom(@PathVariable(name = "roomname") String roomname,
                                                          @PathVariable(name = "userRole") String userRole,
                                                          @PathVariable(name = "username") String username) {
        chatService.addUserWithRoleToChatRoom(roomname, userRole, username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
