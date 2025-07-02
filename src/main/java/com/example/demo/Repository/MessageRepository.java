package com.example.demo.Repository;

import com.example.demo.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    
    // For fetching all messages for a user (sender or receiver)
    List<Message> findBySenderEmailOrReceiverEmail(String senderEmail, String receiverEmail);
    
    // For fetching conversation between two specific users
    @Query("SELECT m FROM Message m WHERE " +
           "(m.senderEmail = :user1 AND m.receiverEmail = :user2) OR " +
           "(m.senderEmail = :user2 AND m.receiverEmail = :user1) " +
           "ORDER BY m.sentAt ASC")
    List<Message> findConversationMessages(@Param("user1") String user1, @Param("user2") String user2);
}