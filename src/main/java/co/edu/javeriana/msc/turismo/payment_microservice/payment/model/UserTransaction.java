package co.edu.javeriana.msc.turismo.payment_microservice.payment.model;

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
    private String userId;
    private Double price;
    private Status orderStatus;
    private PaymentStatus paymentStatus;
}