package co.edu.javeriana.msc.turismo.payment_microservice.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import co.edu.javeriana.msc.turismo.payment_microservice.payment.dto.UserTransactionRequest;

@Service
public class MessageQueueService {
    // This attribute will store the queue name,
    // which is obtained from
    // the property spring.cloud.stream.bindings.sendMessage-out-0.destination
    // from the application.yml file
    @Value("${spring.cloud.stream.bindings.sendStatus-out-0.destination}")
    private String queueName;
    @Autowired
    private StreamBridge streamBridge;
    public boolean sendStatus(UserTransactionRequest orderInformation) {
        return streamBridge.send(queueName, MessageBuilder.withPayload(orderInformation).build());
    }
}