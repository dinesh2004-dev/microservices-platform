package com.example.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnPaymentServiceStatus() throws Exception {
        mockMvc.perform(get("/api/payments/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.service").value("payment-service"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.port").value("8083"));
    }

    @Test
    void shouldProcessPaymentSuccessfully() throws Exception {
        String requestBody = "{\"orderId\":\"ORD-12345\",\"amount\":99.99}";

        mockMvc.perform(post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"))
                .andExpect(jsonPath("$.orderId").value("ORD-12345"))
                .andExpect(jsonPath("$.transactionId").isNotEmpty());
    }

    @Test
    void shouldRejectInvalidPaymentAmount() throws Exception {
        String invalidBody = "{\"orderId\":\"ORD-12345\",\"amount\":-10.00}";

        mockMvc.perform(post("/api/payments/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid payment parameters"));
    }
}