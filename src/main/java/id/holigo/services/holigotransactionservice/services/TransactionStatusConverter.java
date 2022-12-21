package id.holigo.services.holigotransactionservice.services;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.web.model.TransactionFilterEnum;

public class TransactionStatusConverter {

    public static TransactionFilterEnum combinedStatus(PaymentStatusEnum paymentStatusEnum, OrderStatusEnum orderStatusEnum){
        boolean sameStatus = orderStatusEnum.equals(OrderStatusEnum.PROCESS_BOOK)
                || orderStatusEnum.equals(OrderStatusEnum.BOOKED);

        if (paymentStatusEnum.equals(PaymentStatusEnum.SELECTING_PAYMENT)
                && sameStatus){
            return TransactionFilterEnum.SELECTING_PAYMENT;

        }else if (paymentStatusEnum.equals(PaymentStatusEnum.WAITING_PAYMENT)
                && sameStatus){
            return TransactionFilterEnum.WAITING_PAYMENT;

        }else if (paymentStatusEnum.equals(PaymentStatusEnum.PAID)
                &&orderStatusEnum.equals(OrderStatusEnum.BOOKED)){
            return TransactionFilterEnum.PAID;

        }else if (paymentStatusEnum.equals(PaymentStatusEnum.PAID)
                &&(orderStatusEnum.equals(OrderStatusEnum.PROCESS_ISSUED)
                ||orderStatusEnum.equals(OrderStatusEnum.WAITING_ISSUED)
                ||orderStatusEnum.equals(OrderStatusEnum.RETRYING_ISSUED))){
            return TransactionFilterEnum.PROCESS_ISSUED;

        } else if (paymentStatusEnum.equals(PaymentStatusEnum.PAID)
                &&orderStatusEnum.equals(OrderStatusEnum.ISSUED)) {
            return TransactionFilterEnum.ISSUED;

        }else if ((paymentStatusEnum.equals(PaymentStatusEnum.PAID)
                ||paymentStatusEnum.equals(PaymentStatusEnum.PROCESS_REFUND)
                ||paymentStatusEnum.equals(PaymentStatusEnum.WAITING_REFUND)
                ||paymentStatusEnum.equals(PaymentStatusEnum.REFUNDED))
                &&orderStatusEnum.equals(OrderStatusEnum.ISSUED_FAILED)){
            return TransactionFilterEnum.ISSUED_FAILED;

        }else if (paymentStatusEnum.equals(PaymentStatusEnum.PAYMENT_EXPIRED)
                &&orderStatusEnum.equals(OrderStatusEnum.ORDER_EXPIRED)
                ||orderStatusEnum.equals(OrderStatusEnum.BOOKED)){
            return TransactionFilterEnum.ORDER_EXPIRED;

        } else if (paymentStatusEnum.equals(PaymentStatusEnum.PAYMENT_CANCELED)
                &&orderStatusEnum.equals(OrderStatusEnum.ORDER_CANCELED)) {
            return TransactionFilterEnum.ORDER_CANCELED;
        }
        return null;
    }

}
