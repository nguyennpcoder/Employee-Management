package com.vti.controller;

import com.vti.dto.AccountDto;
import com.vti.entity.Account;
import com.vti.form.AccountCreateForm;
import com.vti.form.AccountFilterForm;
import com.vti.form.AccountUpdateForm;
import com.vti.service.AccountService;
import com.vti.service.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private IAccountService service;

    @GetMapping
    public Page<AccountDto> findAll(Pageable pageable, AccountFilterForm form) {
        Page<Account> page = service.findAll(pageable, form);
        return page.map(account -> mapper.map(account, AccountDto.class));
    }

    @GetMapping("/{id}")
    public AccountDto findById(
            @SortDefault(value = "createdAt", direction = Sort.Direction.DESC) @PathVariable("id") int id) {
        //validate pathbvaliable > 100 => return
        Account account = service.findById(id);
        AccountDto dto = mapper.map(account, AccountDto.class);
        return dto;
    }

    @GetMapping("/admin/{id}")
    public AccountDto adminFindById(
            @SortDefault(value = "createdAt", direction = Sort.Direction.DESC) @PathVariable("id") int id) {
        Account account = service.findById(id);
        AccountDto dto = mapper.map(account, AccountDto.class);
        return dto;
    }

    @PostMapping
    public void create(@RequestBody AccountCreateForm form) {
        service.create(form);
    }

    @PutMapping
    public void update(@RequestBody AccountUpdateForm form) {
        service.update(form);
    }

    @DeleteMapping("/{id}")
    public void deleteById(int id) {
        service.deleteById(id);
    }

    @DeleteMapping
    public void deleteAllById(@RequestBody List<Integer> ids) {
        service.deleteAllById(ids);
    }

}
