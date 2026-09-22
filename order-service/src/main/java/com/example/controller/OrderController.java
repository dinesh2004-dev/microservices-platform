package com.example.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of(
            "service", "order-service",
            "status", "UP",
            "port", "8082"
        ));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody Map<String, Object> orderPayload) {
        String item = (String) orderPayload.get("item");
        Object price = orderPayload.get("price");

        if (item == null || price == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid order payload"));
        }

        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        return ResponseEntity.status(201).body(Map.of(
            "orderId", orderId,
            "item", item,
            "price", price,
            "status", "CREATED"
        ));
    }
}