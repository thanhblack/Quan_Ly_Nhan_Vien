package com.vti.Controller;

import com.vti.DTO.AccountDTO;
import com.vti.Entity.Account;
import com.vti.Form.CreatingAccountForm;
import com.vti.Form.UpdatingAccountForm;
import com.vti.Service.EmailSenderService;
import com.vti.Service.IAccountService;
import com.vti.filter.AccountFilterForm;
import com.vti.validate.IdExists;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
//@Validated
@CrossOrigin("*")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<Page<AccountDTO>> getAllAccounts(Pageable pageable, AccountFilterForm form){
       Page<Account> page = accountService.getAllAccount(pageable, form);
       List<AccountDTO> accountDTOS = modelMapper.map(page.getContent(), new TypeToken<List<AccountDTO>>(){
       }.getType());
        return new ResponseEntity<>(new PageImpl<>(accountDTOS, pageable, page.getTotalElements()), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody @Valid CreatingAccountForm form) {
        accountService.createAccount(form);
        return new ResponseEntity<>("Tao account thanh cong",HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable(name = "id") @IdExists int id, @RequestBody @Valid UpdatingAccountForm form){
        form.setId(id);
        accountService.updateAccount(form);
        return new ResponseEntity<>("update account thanh cong",HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable(name = "id") @IdExists int id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>("delete account thanh cong",HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable(name = "id") @IdExists int id) {
        return new ResponseEntity<>(modelMapper.map(accountService.getAccountById(id), AccountDTO.class), HttpStatus.OK);

    }
}
