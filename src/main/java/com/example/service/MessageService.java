package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;


@Service
public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    AccountRepository accountRepository;

    // #3 
    public Message postMessage(Message message) {
        // checks
        if(message.getMessageText() == null || message.getMessageText().isEmpty() || message.getMessageText().length() > 255) {
            return null;
        }
        // check if postedBy is valid
        if (!accountRepository.existsById(message.getPostedBy())) {
            return null;
        }

        return messageRepository.save(message);
    }

    // #4
    public List<Message> getMessageList(){
        return messageRepository.findAll();
    }

    // #5
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    // #6
    public Integer deleteMessage(Integer messageId) {
        // check if the message exists
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isEmpty()) {
            return 0;
        }

        messageRepository.deleteById(messageId);
        return 1;
    }

    // #7
    public Integer updateMessage(Integer messageId, String newMessageText) {
        // checks/conditions
        if (newMessageText.isBlank() || newMessageText.length() > 255) {
            return 0;
        }
    
        // check if the message exists
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isEmpty()) {
            return 0;
        }
    
        // update message
        Message message = optionalMessage.get();
        message.setMessageText(newMessageText);
        messageRepository.save(message);
        return 1;
    }

    // #8
    public List<Message> getMessagesByPostedBy(Integer postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }
    
}
