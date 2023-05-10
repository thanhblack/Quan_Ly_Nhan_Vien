package com.vti.validate;

import com.vti.Service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameNotExistsValidator implements ConstraintValidator<NameNotExists, String> {
    @Autowired
    private IDepartmentService departmentService;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !departmentService.isDepartmentExistsByName(name);
    }
}
