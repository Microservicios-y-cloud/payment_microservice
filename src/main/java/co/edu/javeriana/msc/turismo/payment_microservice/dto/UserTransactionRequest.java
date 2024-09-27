package co.edu.javeriana.msc.turismo.payment_microservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserTransactionRequest {
    private Integer orderId;
    private Integer userId;
    private Integer amount;
}
