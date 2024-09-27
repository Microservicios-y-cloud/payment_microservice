package co.edu.javeriana.msc.turismo.payment_microservice.Repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import co.edu.javeriana.msc.turismo.payment_microservice.model.UserTransaction;

public interface UserTransactionRepository extends MongoRepository<UserTransaction, Integer> {
    Optional<UserTransaction> findByOrderId(Integer orderId);  
}