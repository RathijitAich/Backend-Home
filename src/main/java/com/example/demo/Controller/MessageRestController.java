package com.example.demo.Controller;

import com.example.demo.Entity.Message;
import com.example.demo.Entity.Homeowner;
import com.example.demo.Entity.MaintenanceWorker;
import com.example.demo.Repository.MessageRepository;
import com.example.demo.Repository.HomeownerRepository;
import com.example.demo.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class MessageRestController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private WorkerRepository maintenanceWorkerRepository;

    @GetMapping("/messages/user/{userEmail}")
    public List<Message> getUserMessages(@PathVariable String userEmail) {
        System.out.println("Fetching messages for user: " + userEmail);
        List<Message> messages = messageRepository.findBySenderEmailOrReceiverEmail(userEmail, userEmail);
        System.out.println("Found " + messages.size() + " messages for user: " + userEmail);
        return messages;
    }

    @GetMapping("/conversation")
    public List<Message> getConversationMessages(@RequestParam String user1, @RequestParam String user2) {
        System.out.println("Fetching conversation between: " + user1 + " and " + user2);
        List<Message> messages = messageRepository.findConversationMessages(user1, user2);
        System.out.println("Found " + messages.size() + " messages in conversation");
        return messages;
    }

    @PostMapping("/StartConversation")
    public Message startConversation(@RequestBody Map<String, Object> requestData) {
        System.out.println("Starting conversation with data: " + requestData);

        String senderEmail = (String) requestData.get("senderEmail");
        String receiverEmail = (String) requestData.get("receiverEmail");
        String receiverType = (String) requestData.get("receiverType"); // "homeowner" or "worker"
        String messageContent = (String) requestData.get("messageContent");

        // Validate receiver exists based on type
        if ("homeowner".equals(receiverType)) {
            Homeowner homeowner = homeownerRepository.findByEmail(receiverEmail);
            if (homeowner == null) {
                throw new RuntimeException("Homeowner not found!");
            }
        } else if ("worker".equals(receiverType)) {
            MaintenanceWorker worker = maintenanceWorkerRepository.findByEmail(receiverEmail);
            if (worker == null) {
                throw new RuntimeException("Worker not found!");
            }
        } else {
            throw new RuntimeException("Invalid receiver type!");
        }

        // Create and save message
        Message message = new Message();
        message.setSenderEmail(senderEmail);
        message.setReceiverEmail(receiverEmail);
        message.setMessageContent(messageContent);
        message.setSentAt(LocalDateTime.now());
        message.setIsRead(false);

        Message savedMessage = messageRepository.save(message);
        System.out.println("Message saved: " + savedMessage);

        return savedMessage;
    }
}