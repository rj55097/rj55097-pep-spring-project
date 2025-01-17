package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.AccountRepository;

public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
}
