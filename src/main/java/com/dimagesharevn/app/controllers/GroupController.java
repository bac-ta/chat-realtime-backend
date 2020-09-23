package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.request.GroupSaveRequest;
import com.dimagesharevn.app.models.rests.response.GroupResponse;
import com.dimagesharevn.app.services.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(APIEndpointBase.GROUP_ENDPOINT_BASE)
@Api(
        tags = "Group API"
)
public class GroupController {
    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @ApiOperation(value = "Create a group api", notes = "Create a new group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = APIMessage.CREATE_GROUP_SUCCESSFUL),
            @ApiResponse(code = 409, message = APIMessage.CREATE_GROUP_FAILURE),
    })
    @PostMapping("/create")
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupSaveRequest request) {
        GroupResponse response = groupService.createGroup(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Update a group api", notes = "Update a group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = APIMessage.UPDATE_GROUP_SUCCESSFUL),
            @ApiResponse(code = 409, message = APIMessage.UPDATE_GROUP_FAILURE),
    })
    @PutMapping("/update/{groupName}")
    public ResponseEntity<GroupResponse> updateGroup(@PathVariable("groupName") String groupName, @Valid @RequestBody GroupSaveRequest request) {
        GroupResponse response = groupService.updateGroup(groupName, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
