package org.example.pomodorobuddy.Services;


import org.example.pomodorobuddy.Details.CustomUserDetails;
import org.example.pomodorobuddy.Entities.User;
import org.example.pomodorobuddy.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;


    @Autowired
    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = repository.findByEmail(username);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Convert User to UserDetails (UserDetails)
        User user = userInfo.get();
        return new CustomUserDetails(user);
    }
}
