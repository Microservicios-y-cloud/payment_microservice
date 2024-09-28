package co.edu.javeriana.msc.turismo.payment_microservice.payment.event;

import java.util.Date;
import java.util.UUID;

public interface Event {

    UUID getEventId();

    Date getDate();
}