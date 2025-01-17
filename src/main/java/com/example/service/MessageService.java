package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;


@Service
public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    // // #3 
    // public Message postMessage(Message message) {
    //     return messageRepository.
    // }

    // #4
    public List<Message> getMessageList(){
        return messageRepository.findAll();
    }

    // #5
    public Message getMessageById(Integer messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    // #8
    public List<Message> getMessagesByPostedBy(Integer postedBy) {
        return messageRepository.findByPostedBy(postedBy);
    }
    
}
