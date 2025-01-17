package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.RegistrationException;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
@RequestMapping
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;
    AccountRepository accountRepository;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // #1
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.registerAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(registeredAccount);
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RegistrationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // #2
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account verifiedAccount = accountService.login(account.getUsername(), account.getPassword());
        if (verifiedAccount != null) {
            return ResponseEntity.ok(verifiedAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // #3
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(Message message) {
        Message postedMessage = messageService.postMessage(message);
        if (postedMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postedMessage);
    }

    // #4
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getMessageList() {
        return messageService.getMessageList();
    }

    // #5
    @GetMapping("/messages/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Message getMessageById(@PathVariable Integer messageId) {
        return messageService.getMessageById(messageId);
    }

    // #6 
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        Integer rowsUpdated = messageService.deleteMessage(messageId);

        if (rowsUpdated == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }

    // #7
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message) {
        String newMessageText = message.getMessageText();
        Integer rowsUpdated = messageService.updateMessage(messageId, newMessageText);

        if (rowsUpdated == 1) {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0);
        }
    }

    // #8
    @GetMapping("/accounts/{accountId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Message> getMessagesbyPostedBy(@PathVariable Integer accountId) {
        return messageService.getMessagesByPostedBy(accountId);
    }

}
