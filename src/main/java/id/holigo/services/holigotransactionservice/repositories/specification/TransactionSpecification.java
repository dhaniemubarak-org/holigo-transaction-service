package id.holigo.services.holigotransactionservice.repositories.specification;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import id.holigo.services.holigotransactionservice.web.model.PostpaidEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Component
public class TransactionSpecification {



    public Specification<Transaction> getTransactionType(List<String> prepaidEnums){

        return (root, query, criteriaBuilder) -> {

            if (prepaidEnums.isEmpty()) {
                return criteriaBuilder.and();
            }

            return root.get("transactionType").in(prepaidEnums);
        };

    }

    public Specification<Transaction> getPaymentStatus(List<PaymentStatusEnum> paymentStatus){

        return (root, query, criteriaBuilder) -> {

            if (paymentStatus.isEmpty()) {
                return criteriaBuilder.and();
            }

            return root.get("paymentStatus").in(paymentStatus);
        };
    }

    public Specification<Transaction> getOrderStatus(List<OrderStatusEnum> orderStatus){

        return (root, query, criteriaBuilder) -> {

            if (orderStatus.isEmpty()) {
                return criteriaBuilder.and();
            }

            return root.get("orderStatus").in(orderStatus);
        };
    }

    public Specification<Transaction> getByStartDate(Date startDate){
        return (root, query, criteriaBuilder) -> {
            if (startDate==null){
                return criteriaBuilder.and();
            }else {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), new Timestamp(startDate.getTime()));
            }
        };
    }

    public Specification<Transaction> getByEndDate(Date endDate){
        return (root, query, criteriaBuilder) -> {

            if (endDate == null) {
                return criteriaBuilder.and();
            } else {

                return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), new Timestamp(endDate.getTime()));
            }

        };
    }

    public Specification<Transaction> getDeletedAtNull(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

}
