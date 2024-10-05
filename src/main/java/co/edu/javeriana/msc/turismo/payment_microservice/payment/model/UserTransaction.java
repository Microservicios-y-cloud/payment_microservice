package co.edu.javeriana.msc.turismo.payment_microservice.payment.model;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.PaymentStatus;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document

public class UserTransaction {
    @Id
    private String orderId;
    private Customer user;
    private Double price;
    private Status orderStatus;
    private PaymentStatus paymentStatus;
}