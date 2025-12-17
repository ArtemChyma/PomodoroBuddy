package org.example.pomodorobuddy.Controllers;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.pomodorobuddy.DTOs.RegistrationUserDTO;
import org.example.pomodorobuddy.Entities.User;
import org.example.pomodorobuddy.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationUserDTO userDTO, BindingResult bindingResult) {

        System.out.println("EMAIL: " + userDTO.getEmail() + "\nPASSWORD: " + userDTO.getPassword()
        + "\nPASSWORDCONFIRMATION: " + userDTO.getPasswordConfirmation());

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
        userRepository.save(new User(userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword())));
        return ResponseEntity.ok("Registered");
    }
}
