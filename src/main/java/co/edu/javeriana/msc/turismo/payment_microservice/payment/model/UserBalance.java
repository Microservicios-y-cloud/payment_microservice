package co.edu.javeriana.msc.turismo.payment_microservice.payment.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document

public class UserBalance {
    @Id
    private String id;
    private String userId;
    private Double amount;
}
