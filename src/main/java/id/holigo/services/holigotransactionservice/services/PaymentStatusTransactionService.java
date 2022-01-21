package id.holigo.services.holigotransactionservice.services;

import java.util.UUID;

import org.springframework.statemachine.StateMachine;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.events.PaymentStatusEvent;

public interface PaymentStatusTransactionService {
    StateMachine<PaymentStatusEnum, PaymentStatusEvent> transactionHasBeenPaid(UUID transactionId);
}
