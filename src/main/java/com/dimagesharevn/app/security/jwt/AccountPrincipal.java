package com.dimagesharevn.app.security.jwt;

import com.dimagesharevn.app.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author bac-ta
 */
@Data
@NoArgsConstructor
public class AccountPrincipal implements UserDetails, OAuth2User {
    private String email;
    private String username;
    private String password;
    private String name; //For oauth2
    private Map<String, Object> attributes;//For oath2
    private Collection<? extends GrantedAuthority> authorities;

    public static AccountPrincipal create(User user) {
        String userType = user.getUserType().getName();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(userType));
        AccountPrincipal principal = new AccountPrincipal();

        principal.setEmail(user.getEmail());
        principal.setUsername(user.getUsername());
        principal.setAuthorities(authorities);

        return principal;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}