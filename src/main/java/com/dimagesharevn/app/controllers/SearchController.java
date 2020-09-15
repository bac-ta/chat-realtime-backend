package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.models.entities.Group;
import com.dimagesharevn.app.models.rests.response.SearchResponse;
import com.dimagesharevn.app.models.rests.response.UserFindingResponse;
import com.dimagesharevn.app.services.GroupService;
import com.dimagesharevn.app.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.dimagesharevn.app.enumerations.SearchType.findByValue;

@RestController
@RequestMapping(APIEndpointBase.SEARCH_ENDPOINT_BASE)
@Api(
        tags = "Search API"
)
public class SearchController {
    private UserService userService;
    private GroupService groupService;

    @Autowired
    public SearchController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @ApiOperation(value = "Search api", notes = "Search user, group", response = Object.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 400, message = "")
    })
    @GetMapping
    public ResponseEntity<SearchResponse> search(@ApiParam(name = "searchText", value = "search text input", defaultValue = "") @RequestParam(name = "searchText") String searchText,
                                                 @ApiParam(name = "start", value = "start record pagging") @RequestParam(name = "start", defaultValue = "0", required = false) int start,
                                                 @ApiParam(name = "searchType", value = "type of search  ex: 0 -> ALL, 1 -> USER, 2 -> GROUP") @RequestParam(name = "searchType", defaultValue = "0", required = false) int searchType) {
        //Validate
        findByValue(searchType);

        switch (findByValue(searchType)) {
            case ALL:
                List<UserFindingResponse> userFindingResponses = userService.findUser(searchText, start);
                List<Group> groupResponses = groupService.findGroup(searchText, start);
                SearchResponse searchResponse = new SearchResponse(userFindingResponses, groupResponses);
                return new ResponseEntity<>(searchResponse, HttpStatus.OK);

            case USER:
                List<UserFindingResponse> userFindingResponses2 = userService.findUser(searchText, start);
                SearchResponse searchResponse2 = new SearchResponse(userFindingResponses2, new ArrayList<>());
                return new ResponseEntity<>(searchResponse2, HttpStatus.OK);

            case GROUP:
                List<Group> groupResponses3 = groupService.findGroup(searchText, start);
                SearchResponse searchResponse3 = new SearchResponse(new ArrayList<>(), groupResponses3);
                return new ResponseEntity(searchResponse3, HttpStatus.OK);
            default:
                return null;
        }
    }

}
