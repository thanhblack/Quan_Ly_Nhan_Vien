package com.vti.Specification;

import com.vti.Entity.Account;
import com.vti.filter.AccountFilterForm;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class AccountSpecification {
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String DEPARTMENT_ID = "departmentId";
    private static final String ROLE = "role";

    public static Specification<Account> buildWhere(AccountFilterForm form) {
        if (form == null) {
            return null;
        }
        CustomSpecification whereByUsername = new CustomSpecification(USERNAME, form.getSearch());
        CustomSpecification whereByFirstName = new CustomSpecification(FIRSTNAME, form.getSearch());
        CustomSpecification whereByLastName = new CustomSpecification(LASTNAME, form.getSearch());
        CustomSpecification whereByDepartmentName = new CustomSpecification(DEPARTMENT_ID, form.getDepartmentId());
        CustomSpecification whereByRole = new CustomSpecification(ROLE, form.getRole());

        return Specification.where(whereByUsername.or(whereByFirstName).or(whereByLastName)).and(whereByDepartmentName).and(whereByRole);
    }

    @AllArgsConstructor
    static class CustomSpecification implements Specification<Account> {
        private String key;
        private Object value;

        @Override
        public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if (value == null) {
                return null;
            }
            switch (key) {
                case USERNAME:
                    // WHERE username LIKE "%value%"
                    return criteriaBuilder.like(root.get("userName"), "%" + value.toString().trim() + "%");
                case FIRSTNAME:
                    // WHERE firstName LIKE "%value%"
                    return criteriaBuilder.like(root.get("firstName"), "%" + value.toString().trim() + "%");
                case LASTNAME:
                    // WHERE lastName LIKE "%value%"
                    return criteriaBuilder.like(root.get("lastName"), "%" + value.toString().trim() + "%");
                case DEPARTMENT_ID:
                    return criteriaBuilder.equal(root.get("department").get("id"), value.toString().trim());
                case ROLE:
                    return criteriaBuilder.equal(root.get("role"), value);
                default:
                    return null;
            }
        }
    }
}
