package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.RegistrationException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    
    public Account registerAccount(Account account) {
        // checks
        if (account.getUsername() == null || account.getUsername().isEmpty() || 
                account.getPassword() == null || account.getPassword().length() < 4) {
            throw new RegistrationException();
        }

        // check for duplicate username
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateUsernameException();
        }

        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);    
    }
}
