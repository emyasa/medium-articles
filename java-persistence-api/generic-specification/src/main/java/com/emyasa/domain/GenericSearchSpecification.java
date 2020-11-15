package com.emyasa.domain;

import org.apache.commons.lang3.Validate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GenericSearchSpecification<T> implements Specification<T> {

    private final List<String> columns;
    private final String search;

    public GenericSearchSpecification(List<String> columns, String search) {
        Validate.notNull(columns, "columns must not be null");
        Validate.notNull(search, "search string must not be null");

        this.columns = columns;
        this.search = search;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        for (String column : columns) {
            Expression<String> expression = buildExpression(column, root);
            Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(expression), "%" + search.toUpperCase() + "%");
            predicates.add(predicate);
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }

    private Expression<String> buildExpression(String column, Path<T> path) {
        if (!column.contains("."))
            return path.get(column);

        String[] parts = column.split("\\.");
        int i = 0;
        for (; i < parts.length - 1; i++) {
            path = path.get(parts[i]);
        }

        return path.get(parts[i]);
    }
}
