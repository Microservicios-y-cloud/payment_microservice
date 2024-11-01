package co.edu.javeriana.msc.turismo.payment_microservice.queue;

import java.util.function.Consumer;

import jakarta.ws.rs.ProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserTransactionRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.payment.services.PaymentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class OrderQueueConsumer {

    @Autowired
    PaymentService paymentService;

    @Bean
    Consumer<Message<UserTransactionRequest>> receiveOrder() {


        return message -> {
            try{
                log.info("Received message: {}", message);
                log.info("Payload: {}", message.getPayload());
                // Se crea el evento de pago: Se hace un post del pago a la base de datos,
                //se actualiza el saldo del usuario y el estado de la orden y del pago.
                paymentService.processPayment(message.getPayload());
            } catch (Exception e) {
                log.error("Error processing message: {}", e.getMessage());
                throw new ProcessingException("Error processing payment message. Message received but could not be processed.", e);
            }
        };
    }
}