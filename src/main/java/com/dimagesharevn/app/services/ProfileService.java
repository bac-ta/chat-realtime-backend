package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.entities.Profile;
import com.dimagesharevn.app.models.rests.request.ProfileRequest;
import com.dimagesharevn.app.models.rests.response.ProfileResponse;
import com.dimagesharevn.app.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepository profileRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, AuthenticationService authenticationService){
        this.profileRepository= profileRepository;
        this.authenticationService = authenticationService;
    }


    public ProfileResponse editProfile(ProfileRequest request){
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        Optional<Profile> profileOptional = profileRepository.findById(principal.getUsername());
        if(profileOptional.isPresent()){
            Profile profile = profileOptional.get();
            profile.setName(request.getName());
            profile.setAvatar(request.getAvatar());
            profile.setDescription((request.getDescription()));
            profileRepository.save(profile);
            return new ProfileResponse(APIMessage.UPDATE_PROFILE_SUCCESSFUL);
        } else {
            return new ProfileResponse(APIMessage.UPDATE_PROFILE_FAILURE);
        }
    }

    public Optional<Profile> findByUsername(){
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        return profileRepository.findById(principal.getUsername());
    }

}
