package com.vti.validate;

import com.vti.Service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameNotExistsValidator implements ConstraintValidator<UserNameNotExists, String> {
    @Autowired
    private IAccountService accountService;

    @Override
    public boolean isValid(String userName, ConstraintValidatorContext context) {
        return !accountService.isAccountExistsByUserName(userName);
    }
}
