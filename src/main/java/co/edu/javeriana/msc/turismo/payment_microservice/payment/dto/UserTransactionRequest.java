package co.edu.javeriana.msc.turismo.payment_microservice.payment.dto;

import java.io.Serializable;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.PaymentStatus;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserTransactionRequest implements Serializable{
    private String orderId;
    private String userId;
    private Double amount;
    Status status;
    PaymentStatus paymentStatus;
}
