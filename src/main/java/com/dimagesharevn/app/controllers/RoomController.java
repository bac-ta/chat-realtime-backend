package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.services.RoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIEndpointBase.ROOM_ENDPOINT_BASE)
@Api(
        tags = "Room API"
)
public class RoomController {
    private RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
}
