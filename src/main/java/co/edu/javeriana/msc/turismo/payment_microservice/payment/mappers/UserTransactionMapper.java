package co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.Customer;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserTransactionRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.model.UserTransaction;

public class UserTransactionMapper {

    public static UserTransaction toUserTransaction(UserTransactionRequest request) {
        if (request == null) {
            return null;
        }
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setOrderId(request.getOrderId());
        userTransaction.setUser(request.getUser());
        userTransaction.setPrice(request.getAmount());
        userTransaction.setOrderStatus(request.getStatus());
        userTransaction.setPaymentStatus(request.getPaymentStatus());
        return userTransaction;
    }

    public static UserTransactionRequest toUserTransactionRequest(UserTransaction userTransaction) {
        if (userTransaction == null) {
            return null;
        }
        UserTransactionRequest userTransactionRequest = new UserTransactionRequest();
        userTransactionRequest.setOrderId(userTransaction.getOrderId());
        userTransactionRequest.setUser(userTransaction.getUser());
        userTransactionRequest.setAmount(userTransaction.getPrice());
        userTransactionRequest.setStatus(userTransaction.getOrderStatus());
        userTransactionRequest.setPaymentStatus(userTransaction.getPaymentStatus());
        return userTransactionRequest;
    }
    
}
