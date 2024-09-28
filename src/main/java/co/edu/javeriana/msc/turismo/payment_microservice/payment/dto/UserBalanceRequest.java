package co.edu.javeriana.msc.turismo.payment_microservice.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserBalanceRequest {
    private String id;
    private String userId;
    private Double amount;    
}
