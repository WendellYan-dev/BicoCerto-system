package com.example.apiBicoCerto.utils;

import com.example.apiBicoCerto.entities.InformalWorker;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InformalWorkerSpecs {

    public static Specification<InformalWorker> filter(
            String userName,
            String localService,
            String serviceCategory
    ) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (userName != null && !userName.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("user").get("userName")),
                                "%" + userName.toLowerCase() + "%"
                        )
                );
            }

            if (localService != null && !localService.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("localService")),
                                "%" + localService.toLowerCase() + "%"
                        )
                );
            }

            if (serviceCategory != null && !serviceCategory.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("serviceCategory")),
                                "%" + serviceCategory.toLowerCase() + "%"
                        )
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
