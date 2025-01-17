package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.MessageRepository;

public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }
    
}
