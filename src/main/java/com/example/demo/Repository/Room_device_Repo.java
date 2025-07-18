package com.example.demo.Repository;

import com.example.demo.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Room_device_Repo extends JpaRepository<Room_Device, Long> {
    List<Room_Device> findByRoomRoomName(String roomName);
    List<Room_Device> findByDeviceNameContaining(String deviceName);
}

