package com.ecommerce.authservice.controller;

import com.ecommerce.authservice.model.User;
import com.ecommerce.authservice.repository.UserRepository;
import com.ecommerce.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody User user) {
        user.setRole("ROLE_USER");
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        var userOpt = userRepository.findByUsername(loginRequest.getUsername());
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(loginRequest.getPassword())) {
            String token = jwtService.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(token); // Returns the actual JWT token string
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}