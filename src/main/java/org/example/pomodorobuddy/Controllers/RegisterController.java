package org.example.pomodorobuddy.Controllers;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.example.pomodorobuddy.Entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/register")
public class RegisterController {

    @PostMapping
    public ResponseEntity<String> register(@Valid @ModelAttribute User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("Some errors occurred");
        }

        return ResponseEntity.ok("Registered");
    }
}
