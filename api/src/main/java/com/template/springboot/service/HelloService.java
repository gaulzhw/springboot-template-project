package com.template.springboot.service;

import com.template.springboot.mapper.AccountMapper;
import com.template.springboot.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {
    @Autowired
    private AccountMapper accountMapper;

    public Account getAccountByName(String name) {
        if ("test".equalsIgnoreCase(name)) {
            throw new RuntimeException("test is not valid");
        }
        return accountMapper.getAccountByName(name);
    }
}
