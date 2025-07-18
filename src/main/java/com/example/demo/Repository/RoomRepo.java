package com.example.demo.Repository;

import com.example.demo.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    List<Room> findByHomeownerEmail(String email);

    Room findByRoomName(String roomName);
}