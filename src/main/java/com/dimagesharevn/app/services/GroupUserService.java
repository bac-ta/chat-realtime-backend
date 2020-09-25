package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.models.entities.GroupUser;
import com.dimagesharevn.app.repositories.GroupUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupUserService {
    private final GroupUserRepository groupUserRepository;
    private final AuthenticationService authenticationService;


    @Autowired
    public GroupUserService(GroupUserRepository groupUserRepository, AuthenticationService authenticationService) {
        this.groupUserRepository = groupUserRepository;
        this.authenticationService = authenticationService;
    }

    public List<GroupUser> findListGroupoined() {
        AccountPrincipal principal = authenticationService.getCurrentPrincipal();
        return groupUserRepository.findByUsername(principal.getUsername());
    }

}
