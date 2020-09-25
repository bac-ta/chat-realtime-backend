package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.models.entities.GroupUser;
import com.dimagesharevn.app.services.GroupUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(APIEndpointBase.GROUP_USER_ENPOINT_BASE)
@Api(
        tags = "Group user API"
)
public class GroupUserController {
    private GroupUserService groupUserService;

    @Autowired
    public GroupUserController(GroupUserService groupUserService) {
        this.groupUserService = groupUserService;
    }

    @ApiOperation(value = "Find group joined api", notes = "Find groups that's user joined")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = ""),
    })
    @GetMapping("/joined")
    public ResponseEntity<List<GroupUser>> findGroupJoined() {
        return new ResponseEntity<>(groupUserService.findListGroupoined(), HttpStatus.OK);
    }

}
