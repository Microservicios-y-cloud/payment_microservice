package co.edu.javeriana.msc.turismo.payment_microservice.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.Repository.UserBalanceRespository;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.Repository.UserTransactionRepository;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserBalanceResponse;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserTransactionRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.PaymentStatus;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.enums.Status;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers.UserBalanceMapper;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.mappers.UserTransactionMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class PaymentService {

    private final UserBalanceRespository userBalanceRespository;
    private final UserTransactionRepository userTransactionRespository;

    public PaymentService(UserBalanceRespository userBalanceRespository, UserTransactionRepository userTransactionRespository) {
        this.userBalanceRespository = userBalanceRespository;
        this.userTransactionRespository = userTransactionRespository;
    }

    @Value("${spring.cloud.stream.bindings.sendStatus-out-0.destination}")
    private String queueName;
    @Autowired
    private StreamBridge streamBridge;
    
    public boolean sendStatus(UserTransactionRequest orderInformation) {
        return streamBridge.send(queueName, MessageBuilder.withPayload(orderInformation).build());
    }

    public void processPayment(@Valid UserTransactionRequest userTransactionRequest) {

        // Se obtiene el saldo del usuario

        var userBalance = userBalanceRespository.findByUserId(userTransactionRequest.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User balance not found, with id: " + userTransactionRequest.getUser().getId()));
        // Aquí se implementa la lógica del pago:
        // - Actualizar base de datos
        var userTransaction = UserTransactionMapper.toUserTransaction(userTransactionRequest);
        // - Actualizar saldo del usuario
        if(userBalance.getAmount() < userTransactionRequest.getAmount()) {
            userTransaction.setOrderStatus(Status.RECHAZADA);
            userTransaction.setPaymentStatus(PaymentStatus.RECHAZADA);
            userTransactionRespository.save(userTransaction);
            sendStatus(userTransactionRequest);
            return;
        }
        Double newBalance = userBalance.getAmount() - userTransactionRequest.getAmount();
        userBalance.setAmount(newBalance);
        userBalanceRespository.save(userBalance);
        // - Cambiar estado de la orden y el pago
        userTransaction.setOrderStatus(Status.ACEPTADA);
        userTransaction.setPaymentStatus(PaymentStatus.ACEPTADA);
        userTransactionRespository.save(userTransaction);

        
        sendStatus(UserTransactionMapper.toUserTransactionRequest(userTransaction));
    }

    public String createUserBalance(@Valid UserBalanceRequest request) {
        var userBalance = UserBalanceMapper.toUserBalance(request);
        return userBalanceRespository.save(userBalance).getUser().getId();
    }
}