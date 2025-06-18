package com.example.demo.Controller;

import com.example.demo.Entity.Homeowner;
import com.example.demo.Repository.HomeownerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.MaintenanceWorker;
import com.example.demo.Repository.WorkerRepository;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private WorkerRepository workerRepository;


    @PostMapping
    public ResponseEntity<Map<String, String>> registerHomeowner(@RequestBody Homeowner homeowner) {
        Map<String, String> response = new HashMap<>();

        // Check if email already exists
        if (homeownerRepository.findByEmail(homeowner.getEmail()) != null) {
            response.put("message", "Email already registered.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // Save the new homeowner
        homeownerRepository.save(homeowner);
        response.put("message", "Homeowner registered successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/worker")
    public ResponseEntity<Map<String, String>> registerWorker(@RequestBody MaintenanceWorker worker) {
        Map<String, String> response = new HashMap<>();

        // Check if email already exists
        if (workerRepository.findByEmail(worker.getEmail()) != null) {
            response.put("message", "Email already registered.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // Save the new maintenance worker
        workerRepository.save(worker);
        response.put("message", "Maintenance worker registered successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}