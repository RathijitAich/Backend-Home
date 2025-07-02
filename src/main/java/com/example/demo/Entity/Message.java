package com.example.demo.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageContent;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "is_read")
    private Boolean isRead = false;

    // Foreign key to sender (can be homeowner or worker)
    @Column(name = "sender_email")
    private String senderEmail;

    // Foreign key to receiver (can be homeowner or worker)
    @Column(name = "receiver_email")
    private String receiverEmail;

    // Optional reference for identifying if email belongs to a homeowner
    @ManyToOne
    @JoinColumn(name = "homeowner_email", referencedColumnName = "email", nullable = true)
    @JsonIgnore
    private Homeowner homeowner;

    // Optional reference for identifying if email belongs to a worker
    @ManyToOne
    @JoinColumn(name = "worker_email", referencedColumnName = "email", nullable = true)
    @JsonIgnore
    private MaintenanceWorker maintenanceWorker;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public Homeowner getHomeowner() {
        return homeowner;
    }

    public void setHomeowner(Homeowner homeowner) {
        this.homeowner = homeowner;
    }

    public MaintenanceWorker getMaintenanceWorker() {
        return maintenanceWorker;
    }

    public void setMaintenanceWorker(MaintenanceWorker maintenanceWorker) {
        this.maintenanceWorker = maintenanceWorker;
    }



    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageContent='" + messageContent + '\'' +
                ", sentAt=" + sentAt +
                ", isRead=" + isRead +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                '}';
    }

}
