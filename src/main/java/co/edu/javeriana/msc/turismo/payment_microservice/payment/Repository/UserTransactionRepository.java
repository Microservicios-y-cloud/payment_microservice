package co.edu.javeriana.msc.turismo.payment_microservice.payment.Repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.model.UserTransaction;

public interface UserTransactionRepository extends MongoRepository<UserTransaction, String> {
    Optional<UserTransaction> findByOrderId(Integer orderId);  
}