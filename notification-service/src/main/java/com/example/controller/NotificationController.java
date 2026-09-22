package com.example.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of(
            "service", "notification-service",
            "status", "UP",
            "port", "8084"
        ));
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendNotification(@RequestBody Map<String, String> payload) {
        String recipient = payload.get("recipient");
        String message = payload.get("message");

        if (recipient == null || message == null || recipient.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Recipient and message must not be blank"
            ));
        }

        String notificationId = "NOTIF-" + UUID.randomUUID().toString().substring(0, 8);
        return ResponseEntity.ok(Map.of(
            "notificationId", notificationId,
            "recipient", recipient,
            "status", "DELIVERED"
        ));
    }
}