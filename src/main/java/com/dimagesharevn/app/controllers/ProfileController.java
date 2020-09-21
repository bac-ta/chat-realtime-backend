package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.rests.request.ProfileRequest;
import com.dimagesharevn.app.models.rests.response.ProfileResponse;
import com.dimagesharevn.app.services.ProfileService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
public class ProfileController {
    private final ProfileService profileService;
    @Autowired
    public ProfileController(ProfileService profileService){
        this.profileService= profileService;
    }

    @ApiOperation(value = "Profile api", notes = "Update Profile", response = ProfileResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = APIMessage.UPDATE_POST_SUCCESSFUL),
            @ApiResponse(code = 400, message = APIMessage.UPDATE_POST_FAILURE),
    })

    @PutMapping(value = "/update-profile/{username}")
    public ResponseEntity<ProfileResponse> editProfile(@Valid @RequestBody ProfileRequest request, @PathVariable String username){
        ProfileResponse response = profileService.editProfile(request, username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
