package com.vti.Service;

import com.vti.Entity.Account;
import com.vti.Entity.Department;
import com.vti.Form.CreatingDepartmentForm;
import com.vti.Form.UpdatingDepartmentForm;
import com.vti.Repository.IAccountRepository;
import com.vti.Repository.IDepartmentRepository;
import com.vti.Specification.DepartmentSpecification;
import com.vti.filter.DepartmentFilterForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

//    get all dung page
//    @Override
//    public Page<Department> getAllDepartment(Pageable pageable, DepartmentFilterForm form) {
//        Specification<Department> specification = DepartmentSpecification.buildWhere(form);
//        return departmentRepository.findAll(specification, pageable);
//    }

//    get all dung list
    @Override
    public List<Department> getAllDepartment(DepartmentFilterForm form) {
        Specification<Department> specification = DepartmentSpecification.buildWhere(form);
        return departmentRepository.findAll(specification);
    }

    @Override
    public boolean isDepartmentExistsByName(String name) {
        return departmentRepository.existsByName(name);
    }

    @Override
    public void createDepartment(CreatingDepartmentForm form) {
        Department department = departmentRepository.save(modelMapper.map(form, Department.class));
        List<Account> accounts = new ArrayList<>();
        List<CreatingDepartmentForm.AccountForm> accountForms = form.getAccounts();
        accountForms.forEach(accountForm -> {
            Account account = modelMapper.map(accountForm, Account.class);
            account.setDepartment(department);
            account.setDepartment(departmentRepository.findById(accountForm.getDepartmentId()));
            accounts.add(account);
        });
        accountRepository.saveAll(accounts);
    }

    @Override
    public void updateDepartment(UpdatingDepartmentForm form) {
        departmentRepository.save(modelMapper.map(form, Department.class));
    }

    @Override
    public void deleteDepartment(int id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentRepository.findById(id);
    }
}
