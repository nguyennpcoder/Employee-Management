package com.vti.specification;

import com.vti.entity.Account;
import com.vti.form.AccountFilterForm;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> buildSpec(AccountFilterForm form){
        if (form == null) {
            return null;
        }
        // NOT LAMDA
        // return new Specification<Account>() {
        //            @Override
        //            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        //                List<Predicate> predicates = new ArrayList<>();
        //                // searchByUsername username LIKE '%search%'
        //                if(StringUtils.hasText(form.getSearch())){
        //                    predicates.add(
        //                            builder.like(
        //                                    root.get("username"),
        //                                    "%" + form.getSearch() + "%"
        //                            )
        //                    );
        //                }

        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // searchByUsername username LIKE '%search%'

            if(StringUtils.hasText(form.getSearch())){
                predicates.add(
                        builder.or(
                                builder.like(
                                        root.get("username"),
                                        "%" + form.getSearch() + "%"
                                ),
                                builder.like(
                                        root.get("firstName"),
                                        "%" + form.getSearch() + "%"
                                ),
                                builder.like(
                                        root.get("lastName"),
                                        "%" + form.getSearch() + "%"
                                )
                        )

                );
            }

            if(form.getMinId() != null){
                predicates.add(
                        builder.greaterThanOrEqualTo(
                                root.get("id"),
                                form.getMinId())
                );
            }

            if(form.getMaxId() != null){
                predicates.add(
                        builder.lessThanOrEqualTo(
                                root.get("id"),
                                form.getMaxId())
                );
            }

            if(form.getRole() != null){
                predicates.add(
                        builder.equal(
                                root.get("role"),
                                form.getRole())
                );
            }
            // searchByUsername
            // searchById
            // => Where C1 AND C2
            // builder.and chỉ nhận 1 mảng, ko nhận list
            // => cần chuyển từ mảng sang list
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
