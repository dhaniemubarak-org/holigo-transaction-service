package id.holigo.services.holigotransactionservice.services;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.events.PaymentStatusEvent;
import org.springframework.statemachine.StateMachine;

import java.util.UUID;

public interface PaymentStatusTransactionService {
    StateMachine<PaymentStatusEnum, PaymentStatusEvent> transactionHasBeenPaid(UUID transactionId);

    StateMachine<PaymentStatusEnum, PaymentStatusEvent> paymentHasExpired(UUID transactionId);

    StateMachine<PaymentStatusEnum, PaymentStatusEvent> paymentHasCanceled(UUID transactionId);
}
