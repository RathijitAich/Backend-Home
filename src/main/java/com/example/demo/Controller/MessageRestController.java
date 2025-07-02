package com.example.demo.Controller;

import com.example.demo.Entity.Message;
import com.example.demo.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;

@RestController // Separate REST controller for HTTP endpoints
@RequestMapping("/api") // Add base mapping
public class MessageRestController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/messages/user/{userEmail}") // this is when user loads the whole page
    public List<Message> getUserMessages(@PathVariable String userEmail) {
        System.out.println("Fetching messages for user: " + userEmail);
        List<Message> messages = messageRepository.findBySenderEmailOrReceiverEmail(userEmail, userEmail);
        System.out.println("Found " + messages.size() + " messages for user: " + userEmail);
        
        System.out.println("data sent to frontend: " + messages);
        

        return messages;
    }

    @GetMapping("/conversation") // this is when user selects a conversation
    public List<Message> getConversationMessages(@RequestParam String user1, @RequestParam String user2) {
        System.out.println("Fetching conversation between: " + user1 + " and " + user2);
        List<Message> messages = messageRepository.findConversationMessages(user1, user2);
        System.out.println("Found " + messages.size() + " messages in conversation");
       
        return messages;
    }
}
