package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.models.rests.request.ChatRoomRequest;
import com.dimagesharevn.app.models.rests.response.RoomResponse;
import com.dimagesharevn.app.services.RoomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(APIEndpointBase.ROOM_ENDPOINT_BASE)
@Api(
        tags = "Room API"
)
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @ApiOperation(value = "Find rooms api", notes = "Find list room that are user can join", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 404, message = "")
    })
    @GetMapping("/getRooms")
    public ResponseEntity<List<RoomResponse>> findRooms(@RequestParam(name = "searchText", defaultValue = "") String searchText,
                                                        @RequestParam(name = "start", defaultValue = "0") int start) {
        List<RoomResponse> roomResponses = roomService.findRooms(searchText, start);
        return new ResponseEntity<>(roomResponses, HttpStatus.OK);

    }

    @ApiOperation(value = "Add user with role to chat room API", notes = "Add uset with role to chat room api")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @PostMapping("/addUser/{roomname}/{userRole}/{username}")
    public ResponseEntity<Void> addUserWithRoleToChatRoom(@PathVariable(name = "roomname") String roomname,
                                                          @PathVariable(name = "userRole") String userRole,
                                                          @PathVariable(name = "username") String username) {
        roomService.addUserWithRoleToChatRoom(roomname, userRole, username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Create chat room API", notes = "Create chat room api that can add user to room")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @PostMapping("/create")
    public ResponseEntity<Void> createChatRoom(@Valid @RequestBody ChatRoomRequest request) {
        roomService.createChatRoom(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
