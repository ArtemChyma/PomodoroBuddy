package org.example.pomodorobuddy.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationUserDTO {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    private String passwordConfirmation;

    public RegistrationUserDTO() {}

    public RegistrationUserDTO(String email, String password, String passwordConfirmation) {
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public boolean isPasswordMatch() {
        return password.equals(passwordConfirmation);
    }

    public @NotBlank String getEmail() {
        return email;
    }

    public @NotBlank @Size(min = 8) String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }
}
