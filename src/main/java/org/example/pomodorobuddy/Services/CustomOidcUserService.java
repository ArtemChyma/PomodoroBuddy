package org.example.pomodorobuddy.Services;

import org.example.pomodorobuddy.Entities.User;
import org.example.pomodorobuddy.Repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserRepository userRepository;

    public CustomOidcUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = new DefaultOidcUser(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                userRequest.getIdToken()
        );

        String googleId = oidcUser.getSubject();
        String email = oidcUser.getEmail();
        String roles = "ROLE_USER";
        String image = oidcUser.getPicture();

        Optional<User> checkUser = userRepository.findByEmail(email);
        if (checkUser.isPresent()) {
            if (checkUser.get().getGoogleId() != null)
                return oidcUser;
            throw new OAuth2AuthenticationException("EMAIL IS ALREADY IN USE");
        }

        User user = new User(email, roles, googleId, image);
        userRepository.save(user);

        return oidcUser;
    }
}
