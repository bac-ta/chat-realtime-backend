package com.dimagesharevn.app.controllers;

import com.dimagesharevn.app.constants.APIEndpointBase;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.entities.Profile;
import com.dimagesharevn.app.models.rests.request.ProfileRequest;
import com.dimagesharevn.app.models.rests.response.ProfileResponse;
import com.dimagesharevn.app.repositories.ProfileRepository;
import com.dimagesharevn.app.services.AuthenticationService;
import com.dimagesharevn.app.services.FileService;
import com.dimagesharevn.app.services.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(APIEndpointBase.PROFILE_ENDPOINT_BASE)
@Api(
        tags = "Profile API"
)
public class ProfileController {
    private final ProfileService profileService;
    private final FileService fileService;
    private final AuthenticationService authenticationService;
    private ProfileRepository profileRepository;

    @Autowired
    public ProfileController(ProfileService profileService, FileService fileService, AuthenticationService authenticationService, ProfileRepository profileRepository) {
        this.profileService = profileService;
        this.fileService = fileService;
        this.authenticationService = authenticationService;
        this.profileRepository = profileRepository;
    }

    @ApiOperation(value = "Profile api", notes = "Update Profile", response = ProfileResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = APIMessage.UPDATE_PROFILE_SUCCESSFUL),
            @ApiResponse(code = 400, message = APIMessage.UPDATE_PROFILE_FAILURE),
    })

    @PutMapping(value = "/update-profile")
    public ResponseEntity<ProfileResponse> editProfile(@Valid @RequestBody ProfileRequest request) {
        ProfileResponse response = profileService.editProfile(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = "Profile api", notes = "Find profile")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = APIMessage.UPDATE_PROFILE_SUCCESSFUL),
            @ApiResponse(code = 400, message = APIMessage.UPDATE_PROFILE_FAILURE),
    })
    @GetMapping("/get-profile")
    public ResponseEntity<Optional<Profile>> findProfile() {
        return new ResponseEntity<>(profileService.findByUsername(), HttpStatus.OK);
    }

}

