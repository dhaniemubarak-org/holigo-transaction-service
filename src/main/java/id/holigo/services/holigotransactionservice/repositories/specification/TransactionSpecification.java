package id.holigo.services.holigotransactionservice.repositories.specification;

import id.holigo.services.common.model.OrderStatusEnum;
import id.holigo.services.common.model.PaymentStatusEnum;
import id.holigo.services.holigotransactionservice.domain.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Component
public class TransactionSpecification {

    public Specification<Transaction> getPaymentStatus(List<PaymentStatusEnum> paymentStatus){

        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if (paymentStatus.isEmpty()){
                    return criteriaBuilder.and();
                }

                return root.get("paymentStatus").in(paymentStatus);
            }
        };
    }

    public Specification<Transaction> getOrderStatus(List<OrderStatusEnum> orderStatus){

        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if (orderStatus.isEmpty()){
                    return criteriaBuilder.and();
                }

                return root.get("orderStatus").in(orderStatus);
            }
        };
    }

    public Specification<Transaction> getByStartDate(Date startDate){
        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (startDate==null){
                    return criteriaBuilder.and();
                }else {
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), new Timestamp(startDate.getTime()));
                }
            }
        };
    }

    public Specification<Transaction> getByEndDate(Date endDate){
        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                if (endDate==null){
                    return criteriaBuilder.and();
                } else {

                    return criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), new Timestamp(endDate.getTime()));
                }

            }
        };
    }

    public Specification<Transaction> getDeletedAtNull(){
        return new Specification<Transaction>() {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.isNull(root.get("deletedAt"));
            }
        };
    }

}
