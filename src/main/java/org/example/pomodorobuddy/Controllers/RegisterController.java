package org.example.pomodorobuddy.Controllers;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.pomodorobuddy.DTOs.RegistrationUserDTO;
import org.example.pomodorobuddy.Entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegisterController {

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute RegistrationUserDTO userDTO, BindingResult bindingResult) {

        System.out.println("EMAIL: " + userDTO.getEmail() + "\nPASSWORD: " + userDTO.getPassword()
        + "\nPASSWORDCONFIRMATION: " + userDTO.getPasswordConfirmation());

        if (bindingResult.hasErrors()) {
            System.out.println("Some errors occurred");
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        }

        return ResponseEntity.ok("Registered");
    }
}
