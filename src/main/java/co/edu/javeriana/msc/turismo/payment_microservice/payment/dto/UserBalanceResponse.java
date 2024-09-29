package co.edu.javeriana.msc.turismo.payment_microservice.payment.dto;

public record UserBalanceResponse(
        String id,
        String userId,
        Double amount
) {
}