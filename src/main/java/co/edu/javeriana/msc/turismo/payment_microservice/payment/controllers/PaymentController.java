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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



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

        @PostMapping("/random")
        public ResponseEntity<String> createRandomUserBalance(
            @Valid @RequestBody UserBalanceRequest userBalanceRequest) {
            String createdUserBalance = paymentService.createRandomUserBalance(userBalanceRequest);
            return new ResponseEntity<>(createdUserBalance, HttpStatus.CREATED);
        }

        @PutMapping("/random/{id}")
        public ResponseEntity<String> updateRandomUserBalance(
            @PathVariable String id) {
            String updatedUserBalance = paymentService.updateRandomUserBalance(id);
            return new ResponseEntity<>(updatedUserBalance, HttpStatus.OK);
        }
}