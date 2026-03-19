package com.example.apiBicoCerto.utils;

import com.example.apiBicoCerto.entities.Work;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WorkSpecs {

    public static Specification<Work> filter(
            String title,
            BigDecimal price
    ) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + title.toLowerCase() + "%"
                        )
                );
            }

            if (price != null) {
                predicates.add(
                        cb.between(root.get("price"), BigDecimal.ZERO, price)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}