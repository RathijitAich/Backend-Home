package com.example.demo.Controller;

import com.example.demo.Entity.Room;
import com.example.demo.Entity.Room_Device;
import com.example.demo.Entity.Homeowner;
import com.example.demo.Repository.RoomRepo;
import com.example.demo.Repository.Room_device_Repo;
import com.example.demo.Repository.HomeownerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/room-setup")
public class RoomSetupController {

    @Autowired
    private RoomRepo roomRepo;
    @Autowired
    private Room_device_Repo roomDeviceRepo;
    @Autowired
    private HomeownerRepository homeownerRepository;

    //this is for adding a new room
    @PostMapping("/room")
    public ResponseEntity<Map<String, String>> addRoom(@RequestBody Room room) {
       
      // first check the room name unique or not
        Room existingRoom = roomRepo.findByRoomName(room.getRoomName());
        if (existingRoom != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Room name already exists"));
        }

        // Set the homeowner - Fix: Get homeowner by email string
        Homeowner homeowner = homeownerRepository.findByEmail(room.getHomeowner().getEmail());
        if (homeowner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Homeowner not found"));
        }
        room.setHomeowner(homeowner); // Fix: Set the homeowner object, not email string

        // Set default values
        room.setCreatedAt(LocalDateTime.now());
        // Remove duplicate line: room.setCreatedAt(LocalDateTime.now());
        // room.setTotalDevices(BigDecimal.ZERO); // Remove this line if totalDevices doesn't exist

        // Save the room
        roomRepo.save(room);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Room added successfully");
        return ResponseEntity.ok(response);

    }

    // saving multiple device in a room it will come as a list . 
@PostMapping("/devices")
public ResponseEntity<Map<String, String>> addDevices(@RequestBody List<Room_Device> roomDevices) {
    if (roomDevices.isEmpty()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "No devices provided"));
    }
    
    // Process each device and set the correct Room reference
    for (Room_Device device : roomDevices) {
        // Get the room name from the device's room object
        String roomName = device.getRoom().getRoomName();
        
        // Find the actual Room entity from database
        Room existingRoom = roomRepo.findByRoomName(roomName);
        if (existingRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Room not found: " + roomName));
        }
        
        // Set the persisted Room entity to the device
        device.setRoom(existingRoom);
    }
    
    // Check if the device already exists in the room
    for (Room_Device device : roomDevices) {
        List<Room_Device> existingDevices = roomDeviceRepo.findByRoomRoomName(device.getRoom().getRoomName());
        for (Room_Device existingDevice : existingDevices) {
            if (existingDevice.getDeviceName().equals(device.getDeviceName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("message", "Device already exists in the room: " + device.getDeviceName()));
            }
        }
    }
    
    // Check for duplicates in device names
    List<String> deviceNames = new ArrayList<>();
    for (Room_Device device : roomDevices) {
        if (deviceNames.contains(device.getDeviceName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Duplicate device name found: " + device.getDeviceName()));
        }
        deviceNames.add(device.getDeviceName());
    }

    // Save each device
    roomDeviceRepo.saveAll(roomDevices);

    Map<String, String> response = new HashMap<>();
    response.put("message", "Devices added successfully");
    return ResponseEntity.ok(response);
}

    // this is to get the whole setup of rooms and devices by homeowner email
    @GetMapping("/setup/{email}")
    public ResponseEntity<Map<String, Object>> getRoomSetup(@PathVariable String email) {
        
        // Check if the homeowner exists - Fix: Use correct method name
        Homeowner homeowner = homeownerRepository.findByEmail(email);
        if (homeowner == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Homeowner not found"));
        }

        // Get all rooms for the homeowner
        List<Room> rooms = roomRepo.findByHomeownerEmail(email);
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No rooms found for this homeowner"));
        }

        // Prepare the response
        Map<String, Object> response = new HashMap<>();
        response.put("homeowner", homeowner);
        response.put("rooms", rooms);
        
        // Fix: You can't call setDevices on Room entity unless it has that method
        // Instead, create a map structure for the response
        List<Map<String, Object>> roomsWithDevices = new ArrayList<>();
        for (Room room : rooms) {
            List<Room_Device> devices = roomDeviceRepo.findByRoomRoomName(room.getRoomName());
            Map<String, Object> roomData = new HashMap<>();
            roomData.put("room", room);
            roomData.put("devices", devices);
            roomsWithDevices.add(roomData);
        }
        response.put("rooms", roomsWithDevices);

        return ResponseEntity.ok(response);
    }



    // remove room and its associated devices
    @DeleteMapping("/room/{roomName}")
    public ResponseEntity<Map<String, String>> deleteRoom(@PathVariable String roomName) {
        Room room = roomRepo.findByRoomName(roomName);
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Room not found"));
        }

        // Delete associated devices
        List<Room_Device> devices = roomDeviceRepo.findByRoomRoomName(roomName);
        roomDeviceRepo.deleteAll(devices);

        // Delete the room
        roomRepo.delete(room);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Room and associated devices deleted successfully");
        return ResponseEntity.ok(response);
    }
}