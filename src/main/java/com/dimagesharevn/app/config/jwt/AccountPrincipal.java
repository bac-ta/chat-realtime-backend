package com.dimagesharevn.app.config.jwt;

import com.dimagesharevn.app.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;

/**
 * @author bac-ta
 */
@Data
@NoArgsConstructor
public class AccountPrincipal implements UserDetails {
    private String email;
    private String username;
    private String password;
    private String name; //For oauth2
    private Map<String, Object> attributes;//For oath2

    public static AccountPrincipal create(User user) {
        AccountPrincipal principal = new AccountPrincipal();

        principal.setEmail(user.getEmail());
        principal.setUsername(user.getUsername());

        return principal;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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