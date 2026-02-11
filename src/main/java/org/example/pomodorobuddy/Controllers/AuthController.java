package org.example.pomodorobuddy.Controllers;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pomodorobuddy.DTOs.RegistrationUserDTO;
import org.example.pomodorobuddy.Entities.User;
import org.example.pomodorobuddy.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    @Autowired
    public AuthController(PasswordEncoder encoder, UserRepository userRepository,
                          AuthenticationManager authenticationManager) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationUserDTO userDTO, BindingResult bindingResult) {

        Optional<User> user = userRepository.findByEmail(userDTO.getEmail());
        if (user.isPresent()) {
            return ResponseEntity.badRequest().body("USER_TAKEN");
        }
        if (bindingResult.hasErrors()) {
            System.out.println("Some errors occurred");
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }
        if (bindingResult.hasFieldErrors("email")) {
            return ResponseEntity.badRequest().body("EMAIL_BLANK");
        }
        if (bindingResult.hasFieldErrors("password")) {
            return ResponseEntity.badRequest().body("PASSWORD");
        }
        if (!userDTO.isPasswordMatch()) {
            return ResponseEntity.badRequest().body("PASSWORDS_NOT_MATCHING");
        }
        userRepository.save(new User(userDTO.getEmail(), encoder.encode(userDTO.getPassword()), "ROLE_USER"));
        return ResponseEntity.ok("Registered");
    }
}
