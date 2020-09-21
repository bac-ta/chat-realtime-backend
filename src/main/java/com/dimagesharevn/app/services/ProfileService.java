package com.dimagesharevn.app.services;

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
    private FileService fileService;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, FileService fileService){
        this.profileRepository= profileRepository;
        this.fileService = fileService;
    }


    public ProfileResponse editProfile(ProfileRequest request, String username){
        Optional<Profile> profileOptional = profileRepository.findById(username);
        if(profileOptional.isPresent()){
            Profile profile = profileOptional.get();
            profile.setName(request.getName());
            profile.setAvatar(request.getFilename());
            profile.setDescription((request.getDescription()));
            profileRepository.save(profile);
            return new ProfileResponse(APIMessage.UPDATE_PROFILE_SUCCESSFUL);
        } else {
            return new ProfileResponse(APIMessage.UPDATE_PROFILE_FAILURE);
        }
    }
}
