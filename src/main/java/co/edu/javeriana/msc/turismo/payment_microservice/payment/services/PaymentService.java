package co.edu.javeriana.msc.turismo.payment_microservice.payment.services;

import org.springframework.stereotype.Service;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.Repository.UserBalanceRespository;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.Repository.UserTransactionRepository;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserTransactionRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.PaymentStatus;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.Status;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers.UserBalanceMapper;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers.UserTransactionMapper;
import jakarta.validation.Valid;

@Service
public class PaymentService {

    private final UserBalanceRespository userBalanceRespository;
    private final UserTransactionRepository userTransactionRespository;

    public PaymentService(UserBalanceRespository userBalanceRespository, UserTransactionRepository userTransactionRespository) {
        this.userBalanceRespository = userBalanceRespository;
        this.userTransactionRespository = userTransactionRespository;
    }

    public void processPayment(@Valid UserTransactionRequest userTransactionRequest) {



        // Se obtiene el saldo del usuario
        
        var userBalance = userBalanceRespository.findByUserId(userTransactionRequest.getUserId()).get();

        // Aquí se implementa la lógica del pago:
        // - Actualizar base de datos
        var userTransaction = UserTransactionMapper.toOrderPurchase(userTransactionRequest);
        // - Actualizar saldo del usuario
        Double newBalance = userBalance.getAmount() - userTransactionRequest.getAmount();
        // Tal vez poner validaciones de saldo suficiente, etc... Y en base a eso completar la transacción de forma correcta
        userBalance.setAmount(newBalance);
        userBalanceRespository.save(userBalance);
        // - Cambiar estado de la orden y el pago
        userTransaction.setOrderStatus(Status.ACEPTADA);
        userTransaction.setPaymentStatus(PaymentStatus.ACEPTADA);
        userTransactionRespository.save(userTransaction);
    }

    public String createUserBalance(@Valid UserBalanceRequest request) {
        var userBalance = UserBalanceMapper.toUserBalance(request);
        return userBalanceRespository.save(userBalance).getUserId();
    }
}