package co.edu.javeriana.msc.turismo.payment_microservice.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

import co.edu.javeriana.msc.turismo.payment_microservice.dto.UserTransactionRequest;
import co.edu.javeriana.msc.turismo.payment_microservice.enums.PaymentStatus;

@NoArgsConstructor
@Data
public class PaymentEvent implements Event{

    private UUID eventId=UUID.randomUUID();
    private Date eventDate=new Date();
    private UserTransactionRequest userTransactionRequest;
    private PaymentStatus paymentStatus;

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getDate() {
        return eventDate;
    }

    public PaymentEvent(UserTransactionRequest userTransactionRequest, PaymentStatus paymentStatus) {
        this.userTransactionRequest = userTransactionRequest;
        this.paymentStatus = paymentStatus;
    }
}