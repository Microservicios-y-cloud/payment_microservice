package co.edu.javeriana.msc.turismo.payment_microservice.model;

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
    private String userId;
    private int amount;
}
