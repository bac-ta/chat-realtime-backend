package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.models.dto.HistoryDTO;
import com.dimagesharevn.app.models.rests.request.RosterRequest;
import com.dimagesharevn.app.services.ChatService;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(APIEndpointBase.CHAT_ENPOINT_BASE)
@Api(
        tags = "Chat API"
)
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
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

}
