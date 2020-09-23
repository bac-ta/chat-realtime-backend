package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
