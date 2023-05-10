package com.vti.Service;

import com.vti.DTO.AccountDTO;
import com.vti.Entity.Account;
import com.vti.Form.CreatingAccountForm;
import com.vti.Form.UpdatingAccountForm;
import com.vti.Repository.IAccountRepository;
import com.vti.Repository.IDepartmentRepository;
import com.vti.Specification.AccountSpecification;
import com.vti.filter.AccountFilterForm;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<Account> getAllAccount(Pageable pageable, AccountFilterForm form) {
        Specification<Account> specification = AccountSpecification.buildWhere(form);
            return accountRepository.findAll(specification, pageable);
    }

    @Override
    public Account getAccountByUserName(String userName) {
        return accountRepository.findByUserName(userName);
    }

    @Override
    public boolean isAccountExistsByUserName(String userName) {
        return accountRepository.existsByUserName(userName);
    }

    @Override
    public boolean isAccountExistsById(Integer id) {
        return accountRepository.existsById(id);
    }

    @Override
    public void createAccount(CreatingAccountForm form) {
        TypeMap<CreatingAccountForm, Account> typeMap = modelMapper.getTypeMap(CreatingAccountForm.class, Account.class);
        if (typeMap == null){
            modelMapper.addMappings(new PropertyMap<CreatingAccountForm, Account>() {
                @Override
                protected void configure() {
                    skip(destination.getId());
                }
            });
        }
        Account account = modelMapper.map(form, Account.class);
        account.setDepartment(departmentRepository.findById(form.getDepartmentId()));
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(UpdatingAccountForm form) {
        Account account = modelMapper.map(form, Account.class);
        account.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public boolean isAccountExistsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public Account getAccountById(int id) {
        return accountRepository.findById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(username, account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole().toString()));
    }
}
