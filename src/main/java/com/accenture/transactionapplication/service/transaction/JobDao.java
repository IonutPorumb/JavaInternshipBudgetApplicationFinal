package com.accenture.transactionapplication.service.transaction;

import com.accenture.model.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class JobDao {
    private final EntityManager em;

    List<Transaction> findAllByProductAndTypeAndAmountBeforeAndAmountAfterOrderByAmountAmountAsc(String product, String type,
                                                                                                 Double minAmount, Double maxAmount) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = cb.createQuery(Transaction.class);

        Root<Transaction> transactionRoot = criteriaQuery.from(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();
        if (product != null) {
            predicates.add(cb.equal(transactionRoot.get("product"), product));
        }
        if (type != null) {
            predicates.add(cb.equal(transactionRoot.get("type"), type));
        }
        if (type != null) {
            predicates.add(cb.between(transactionRoot.get("amount"), minAmount, maxAmount));
        }
        Predicate[] predicates1 = new Predicate[predicates.size()];
        predicates.toArray(predicates1);
        criteriaQuery.where(predicates1);

        TypedQuery<Transaction> query = em.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
