package co.edu.javeriana.msc.turismo.payment_microservice.payment.model;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed(unique = true)
    private Customer user;
    private Double amount;
}
