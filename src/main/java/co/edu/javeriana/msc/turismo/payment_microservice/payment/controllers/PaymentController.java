package co.edu.javeriana.msc.turismo.payment_microservice.payment.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceResponse;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.services.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor

public class PaymentController {

    private final PaymentService paymentService;
        @PostMapping
        public ResponseEntity<String> createUserBalance(
            @Valid @RequestBody UserBalanceRequest userBalanceRequest) {
            String createdUserBalance = paymentService.createUserBalance(userBalanceRequest);
            return new ResponseEntity<>(createdUserBalance, HttpStatus.CREATED);
        }
}