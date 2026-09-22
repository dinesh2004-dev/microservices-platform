package com.example.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of(
            "service", "auth-service",
            "status", "UP",
            "port", "8081"
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if ("admin".equals(username) && "password".equals(password)) {
            return ResponseEntity.ok(Map.of(
                "token", "mock-jwt-token-auth-agent-ok",
                "message", "Authentication successful"
            ));
        }

        return ResponseEntity.status(401).body(Map.of(
            "error", "Invalid credentials"
        ));
    }
}