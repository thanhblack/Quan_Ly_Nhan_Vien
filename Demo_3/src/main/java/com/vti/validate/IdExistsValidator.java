package com.vti.validate;

import com.vti.Service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdExistsValidator implements ConstraintValidator<IdExists, Integer> {
    @Autowired
    private IAccountService accountService;

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext context) {
        return accountService.isAccountExistsById(id);
    }
}
