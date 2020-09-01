package com.dimagesharevn.app.services;

import com.dimagesharevn.app.configs.jwt.AccountPrincipal;
import com.dimagesharevn.app.models.entities.User;
import com.dimagesharevn.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author bac-ta
 */
@Service
public class UserDetailsImplService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UserDetailsImplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent())
            return AccountPrincipal.create(optionalUser.get());
        return null;
    }
}
