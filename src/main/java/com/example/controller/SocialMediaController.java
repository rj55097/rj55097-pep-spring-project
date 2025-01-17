package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
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

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // @PostMapping("/register")
    // public User register(@RequestBody User newUser) {
    //     // Logic to register a new user
    // }

    // @PostMapping("/login")
    // public User login(@RequestBody LoginRequest loginRequest) {
    //     // Logic to authenticate user login
    // }

    // @GetMapping("/{userId}")
    // public User getUserProfile(@PathVariable Long userId) {
    //     // Logic to retrieve user profile
    // }

    // @PutMapping("/{userId}")
    // public User updateUserProfile(@PathVariable Long userId, @RequestBody User updatedUser) {
    //     // Logic to update user profile
    // }

    // ----------------------------------------------------------------------------------------------------

    // @PostMapping(value = "/register")
    // public Account register(@RequestBody Account newAccount){
    //     return newAccount;
    // }

    // #1
    // @PostMapping("/register")
    // public ResponseEntity<Void> (@RequestBody Account account) {
    //     accountService.register(Account account);
    //     return ResponseEntity.status(HttpStatus.CREATED)
    //         .body("Succesfully registered");
    // }

    // #2 49:30
    // @PostMapping("/login")
    // public ResponseEntity<Void> (@RequestBody Account account) throws AuthenticationException {
    //     accountService.login(account.getUsername(), account.getPassword());
    //     return ResponseEntity.noContent()
    //         .header("username", account.getUsername())
    //         .build();
    // }

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
