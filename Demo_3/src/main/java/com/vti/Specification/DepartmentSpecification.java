package com.vti.Specification;

import com.vti.Entity.Department;
import com.vti.filter.DepartmentFilterForm;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class DepartmentSpecification {
    private static final String searchName = "searchName";
    private static final String MIN_CreatedDate = "minCreatedDate";
    private static final String MAX_CreatedDate = "maxCreatedDate";
    private static final String searchType = "searchType";

    public static Specification<Department> buildWhere(DepartmentFilterForm form){
        if (form == null){
            return null;
        }
        CustomSpecification whereName = new CustomSpecification(searchName, form.getSearchName());
        CustomSpecification whereMinCreatedDate = new CustomSpecification(MIN_CreatedDate, form.getMinCreatedDate());
        CustomSpecification whereMaxCreatedDate = new CustomSpecification(MAX_CreatedDate, form.getMaxCreatedDate());
        CustomSpecification whereType = new CustomSpecification(searchType, form.getType());

        return Specification.where(whereName).or(whereMaxCreatedDate.and(whereMinCreatedDate)).and(whereType);
    }

    @AllArgsConstructor
    static class CustomSpecification implements Specification<Department> {

        private String key;
        private Object value;

        @Override
        public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if (value == null) {
                return null;
            }
            switch (key) {
                case searchName:
                    return criteriaBuilder.like(root.get("name"), "%" + value.toString().trim() + "%");

                case MIN_CreatedDate:
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("createDate").as(java.sql.Date.class), (Date) value);

                case MAX_CreatedDate:
                    return criteriaBuilder.lessThanOrEqualTo(root.get("createDate").as(java.sql.Date.class), (Date) value);

                case searchType:
                    return criteriaBuilder.equal(root.get("type"), value);

                default:
                    return null;

            }
        }
    }
}
