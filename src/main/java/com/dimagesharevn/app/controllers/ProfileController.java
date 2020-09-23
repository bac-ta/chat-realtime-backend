package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.request.ProfileRequest;
import com.dimagesharevn.app.models.rests.response.ProfileResponse;
import com.dimagesharevn.app.services.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(APIEndpointBase.PROFILE_ENDPOINT_BASE)
@Api(
        tags = "Profile API"
)
public class ProfileController {
    private final ProfileService profileService;
    @Autowired
    public ProfileController(ProfileService profileService){
        this.profileService= profileService;
    }

    @ApiOperation(value = "Profile api", notes = "Update Profile", response = ProfileResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = APIMessage.UPDATE_PROFILE_SUCCESSFUL),
            @ApiResponse(code = 400, message = APIMessage.UPDATE_PROFILE_FAILURE),
    })

    @PutMapping(value = "/update-profile")
    public ResponseEntity<ProfileResponse> editProfile(@Valid @RequestBody ProfileRequest request){
        ProfileResponse response = profileService.editProfile(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
