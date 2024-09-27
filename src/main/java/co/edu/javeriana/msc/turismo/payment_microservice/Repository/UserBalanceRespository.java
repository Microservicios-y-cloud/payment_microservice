package co.edu.javeriana.msc.turismo.payment_microservice.Repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import co.edu.javeriana.msc.turismo.payment_microservice.model.UserBalance;

public interface UserBalanceRespository extends MongoRepository<UserBalance, String>{
    Optional<UserBalance> findByClientId(String userId);
}
