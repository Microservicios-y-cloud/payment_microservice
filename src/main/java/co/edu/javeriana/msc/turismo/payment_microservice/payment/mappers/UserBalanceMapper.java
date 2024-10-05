package co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceResponse;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.model.UserBalance;

public class UserBalanceMapper {
        public static UserBalance toUserBalance(UserBalanceRequest request) {
        if (request == null) {
            return null;
        }
        UserBalance userBalance = new UserBalance();
        userBalance.setId(request.getId());
        userBalance.setUser(request.getUser());
        userBalance.setAmount(request.getAmount());
        return userBalance;
    }

    public static UserBalanceResponse toUserBalanceResponse(UserBalance userBalance) {
        if (userBalance == null) {
            return null;
        }
        UserBalanceResponse userBalanceResponse = new UserBalanceResponse(userBalance.getId(), 
                                                                        userBalance.getUser().getId(),
                                                                         userBalance.getAmount());
        return userBalanceResponse;
    }
    
}
