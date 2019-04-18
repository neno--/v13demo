package com.github.nenomm.v13demo.security;


import com.github.nenomm.v13demo.domain.user.Password;
import com.github.nenomm.v13demo.domain.user.User;
import com.github.nenomm.v13demo.domain.user.UserPrivilege;
import com.github.nenomm.v13demo.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private UserRepository userRepository;

    @Autowired
    public CustomAuthenticationProvider(final UserRepository p_userRepository) {
        this.userRepository = p_userRepository;
    }

    @Override
    public Authentication authenticate(final Authentication p_authentication)
            throws AuthenticationException {

        final String username = p_authentication.getName();
        final Password password = Password.getNew(p_authentication.getCredentials().toString());

        logger.info("Trying to authenticate {}", username);

        final User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("email not found");
        } else if (!user.getPassword().equals(password)) {
            throw new BadCredentialsException("invalid password");
        } else {
            logger.info("User login: {}", user.toString());

            List<UserPrivilege> privileges = new ArrayList<>(user.getPrivileges());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, privileges);

            token.setDetails(new CustomUserDetails(user.getId()));

            return token;
        }
    }

    @Override
    public boolean supports(final Class<?> p_authentication) {
        return p_authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
