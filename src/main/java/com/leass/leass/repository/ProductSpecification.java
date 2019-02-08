package com.leass.leass.repository;

import com.leass.leass.model.Product;
import com.leass.leass.service.product.ProductCriteria;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

public class ProductSpecification {

    public Specification<Product> invoiceByQuery(ProductCriteria productCriteria) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                ArrayList<Predicate> restrictions = new ArrayList<Predicate>();

                if (productCriteria.getRent() != null) {
                    if (Boolean.TRUE.equals(productCriteria.getRent())) {
                        restrictions.add(criteriaBuilder.isTrue(root.<Boolean>get("isRent")));
                    }

                    if (Boolean.FALSE.equals(productCriteria.getRent())) {
                        restrictions.add(criteriaBuilder.or(
                                criteriaBuilder.isFalse(root.<Boolean>get("isRent")),
                                criteriaBuilder.isNull(root.get("isRent"))
                        ));
                    }
                }

                return criteriaBuilder.and(restrictions.toArray(new Predicate[]{}));
            }
        };
    }
}
