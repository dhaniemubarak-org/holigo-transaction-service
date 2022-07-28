package id.holigo.services.holigotransactionservice.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.events.OrderStatusEvent;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderStatusTransactionServiceImpl implements OrderStatusTransactionService {

    public static final String TRANSACTION_HEADER = "payment_id";

    @Autowired
    private final TransactionRepository transactionRepository;

    private final OrderStatusTransactionInterceptor orderStatusTransactionInterceptor;

    private final StateMachineFactory<OrderStatusEnum, OrderStatusEvent> orderStatusStateMachineFactory;

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> bookingFail(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.BOOK_FAIL);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> bookingSuccess(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.BOOK_SUCCESS);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> processIssued(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.PROCESS_ISSUED);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> issuedSuccess(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.ISSUED_SUCCESS);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> waitingIssued(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.WAITING_ISSUED);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> retryingIssued(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.RETRYING_ISSUED);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> issuedFail(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.ISSUED_FAIL);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> cancelTransaction(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.ORDER_CANCEL);
        return sm;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderStatusEvent> expiredTransaction(UUID transactionId) {
        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = build(transactionId);
        sendEvent(transactionId, sm, OrderStatusEvent.ORDER_EXPIRE);
        return sm;
    }

    private void sendEvent(UUID id, StateMachine<OrderStatusEnum, OrderStatusEvent> sm,
            OrderStatusEvent event) {
        Message<OrderStatusEvent> message = MessageBuilder.withPayload(event)
                .setHeader(TRANSACTION_HEADER, id).build();
        sm.sendEvent(message);
    }

    private StateMachine<OrderStatusEnum, OrderStatusEvent> build(UUID id) {
        Transaction transaction = transactionRepository.getById(id);

        StateMachine<OrderStatusEnum, OrderStatusEvent> sm = orderStatusStateMachineFactory
                .getStateMachine(transaction.getId().toString());

        sm.stop();
        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(orderStatusTransactionInterceptor);
            sma.resetStateMachine(new DefaultStateMachineContext<OrderStatusEnum, OrderStatusEvent>(
                    transaction.getOrderStatus(), null, null, null));
        });
        sm.start();
        return sm;
    }

}
