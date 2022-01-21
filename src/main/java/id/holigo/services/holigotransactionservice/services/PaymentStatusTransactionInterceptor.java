package id.holigo.services.holigotransactionservice.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.events.PaymentStatusEvent;
import id.holigo.services.holigotransactionservice.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class PaymentStatusTransactionInterceptor extends StateMachineInterceptorAdapter<PaymentStatusEnum, PaymentStatusEvent> {

    private final TransactionRepository transactionRepository;

    @Override
    public void preStateChange(State<PaymentStatusEnum, PaymentStatusEvent> state, Message<PaymentStatusEvent> message,
            Transition<PaymentStatusEnum, PaymentStatusEvent> transition,
            StateMachine<PaymentStatusEnum, PaymentStatusEvent> stateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(
                    UUID.class.cast(UUID
                            .fromString(msg.getHeaders().get(TransactionServiceImpl.TRANSACTION_HEADER).toString())))
                    .ifPresent(id -> {
                        Transaction transaction = transactionRepository.getById(id);
                        transaction.setPaymentStatus(state.getId());
                        transactionRepository.save(transaction);
                    });
        });
    }
}
