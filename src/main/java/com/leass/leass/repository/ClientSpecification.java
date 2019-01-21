package com.leass.leass.repository;

import com.leass.leass.model.Client;
import com.leass.leass.service.client.ClientCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class ClientSpecification {

    public Specification<Client> ClientByQuery(final ClientCriteria clientCriteria) {
        return new Specification<Client>() {
            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> restrictions = new ArrayList<Predicate>();

                return criteriaBuilder.and(restrictions.toArray(new Predicate[]{}));
            }
        };
    }
}
