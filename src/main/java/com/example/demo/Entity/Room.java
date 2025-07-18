package com.example.demo.Entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_name", nullable = false, unique = true)
    private String roomName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column (name = "room_type", nullable = false)
    private String roomType;

    // Foreign key to Homeowner by email
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    private Homeowner homeowner;

    // Getters and Setters
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }

    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Homeowner getHomeowner() { return homeowner; }
    public void setHomeowner(Homeowner homeowner2) { this.homeowner = homeowner2;  }
    public void setTotalDevices(BigDecimal zero) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTotalDevices'");
    }
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
}