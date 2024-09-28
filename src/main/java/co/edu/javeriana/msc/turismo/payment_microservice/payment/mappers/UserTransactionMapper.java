package co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserTransactionRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.model.UserTransaction;

public class UserTransactionMapper {

    public static UserTransaction toOrderPurchase(UserTransactionRequest request) {
        if (request == null) {
            return null;
        }
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setOrderId(request.getOrderId());
        userTransaction.setUserId(request.getUserId());
        userTransaction.setPrice(request.getAmount());
        userTransaction.setOrderStatus(request.getStatus());
        userTransaction.setPaymentStatus(request.getPaymentStatus());
        return userTransaction;
    }
    
}
