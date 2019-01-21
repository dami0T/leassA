package com.leass.leass.repository;

import com.leass.leass.model.Invoice;
import com.leass.leass.service.invoice.InvoiceCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class InvoiceSpecification {

    public Specification<Invoice> invoiceByQuery(InvoiceCriteria invoiceCriteria) {
        return new Specification<Invoice>() {
            @Override
            public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> restrictions = new ArrayList<Predicate>();

                if (invoiceCriteria.getAgreementId() != null) {
                    restrictions.add(criteriaBuilder.equal(root.get("agreement"), invoiceCriteria.getAgreementId()));
                }

                if (invoiceCriteria.getPaid() != null && Boolean.TRUE.equals(invoiceCriteria.getPaid())) {
                    restrictions.add(criteriaBuilder.greaterThan(root.get("finalGrossValue"), root.get("paidValue")));
                }

                return criteriaBuilder.and(restrictions.toArray(new Predicate[]{}));
            }
        };
    }
}
