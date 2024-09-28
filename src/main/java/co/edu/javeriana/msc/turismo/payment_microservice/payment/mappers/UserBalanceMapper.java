package co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.model.UserBalance;

public class UserBalanceMapper {
        public static UserBalance toUserBalance(UserBalanceRequest request) {
        if (request == null) {
            return null;
        }
        UserBalance userBalance = new UserBalance();
        userBalance.setId(request.getId());
        userBalance.setUserId(request.getUserId());
        userBalance.setAmount(request.getAmount());
        return userBalance;
    }
    
}
