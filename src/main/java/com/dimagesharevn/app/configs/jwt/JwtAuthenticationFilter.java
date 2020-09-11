package com.dimagesharevn.app.configs.jwt;

import com.dimagesharevn.app.constants.APIMessage;
import com.dimagesharevn.app.models.caches.JWT;
import com.dimagesharevn.app.services.AuthenticationService;
import com.dimagesharevn.app.services.UserDetailsImplService;
import com.dimagesharevn.app.utils.UnauthorizedExceptionHandler;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author bac-ta
 */
@NoArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserDetailsImplService userDetailsImplService;
    @Autowired
    private AuthenticationService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(httpServletRequest);
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            //Validate jwt
            Optional<JWT> optionalJWT = authService.findByKey(jwt);
            if (!optionalJWT.isPresent())
                throw new UnauthorizedExceptionHandler(APIMessage.ENDTRY_POINT_UNAUTHORIZED);

            String username = tokenProvider.getUsernameFromJWT(jwt);
            UserDetails details = userDetailsImplService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
