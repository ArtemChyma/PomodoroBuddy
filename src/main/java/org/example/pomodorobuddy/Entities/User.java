package org.example.pomodorobuddy.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long Id;
    @Column(name = "email_address", unique = true, nullable = false)
    private String email;
    @Column(name = "password")
    private String password;
    private String roles;
    public User(String email, String password, String roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
