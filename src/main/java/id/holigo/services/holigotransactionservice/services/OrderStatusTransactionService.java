package id.holigo.services.holigotransactionservice.services;

import java.util.UUID;

import org.springframework.statemachine.StateMachine;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.holigotransactionservice.events.OrderStatusEvent;

public interface OrderStatusTransactionService {

    StateMachine<OrderStatusEnum, OrderStatusEvent> bookingFail(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> bookingSuccess(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> processIssued(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> issuedSuccess(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> waitingIssued(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> retryingIssued(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> issuedFail(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> cancelTransaction(UUID transactionId);

    StateMachine<OrderStatusEnum, OrderStatusEvent> expiredTransaction(UUID transactionId);
}
