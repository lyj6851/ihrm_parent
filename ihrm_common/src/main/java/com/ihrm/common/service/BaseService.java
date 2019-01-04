package com.ihrm.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * <p>基础Service</p>
 *
 * @author xiaodongsun
 * @date 2019/01/03
 */
public class BaseService<T> {

    protected Specification<T> getSpect(String companyId){
        Specification<T>  spect = new Specification(){

            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("companyId").as(String.class), companyId);
            }
        };
        return spect;
    }

}
