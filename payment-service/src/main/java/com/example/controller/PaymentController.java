package com.example.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of(
            "service", "payment-service",
            "status", "UP",
            "port", "8083"
        ));
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(@RequestBody Map<String, Object> paymentPayload) {
        String orderId = (String) paymentPayload.get("orderId");
        Number amount = (Number) paymentPayload.get("amount");

        if (orderId == null || amount == null || amount.doubleValue() <= 0) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Invalid payment parameters"
            ));
        }

        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8);
        return ResponseEntity.ok(Map.of(
            "transactionId", transactionId,
            "orderId", orderId,
            "amount", amount,
            "status", "COMPLETED"
        ));
    }
}