package com.leass.leass.repository;

import com.leass.leass.model.Agreement;
import com.leass.leass.service.agreement.AgreementCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class AgreementSpecification {

    public Specification<Agreement> agreementByQuery(final AgreementCriteria agreementCriteria) {
        return new Specification<Agreement>() {
            @Override
            public Predicate toPredicate(Root<Agreement> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> restrictions = new ArrayList<Predicate>();

                if (agreementCriteria.getClientId() != null) {
                    restrictions.add(criteriaBuilder.equal(root.get("client"), agreementCriteria.getClientId()));
                }

                return criteriaBuilder.and(restrictions.toArray(new Predicate[]{}));
            }
        };
    }

}
