package com.vti.validate;

import com.vti.Service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailNotExistsValidator implements ConstraintValidator<EmailNotExists, String> {
    @Autowired
    private IAccountService accountService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !accountService.isAccountExistsByEmail(email);
    }
}
