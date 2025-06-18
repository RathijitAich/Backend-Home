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
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private WorkerRepository workerRepository;

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("_variable_email");
        String password = loginData.get("password");

        System.out.printf("Attempting login for email: %s\n", email);
        

        Homeowner homeowner = homeownerRepository.findByEmail(email);

        Map<String, String> response = new HashMap<>();

        if (homeowner != null && homeowner.getPassword().equals(password)) {
            response.put("message", "Login successful!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Invalid email or password.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/worker")
    public ResponseEntity<Map<String, String>> loginWorker(@RequestBody Map<String, String>
    loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        // Debugging output for email and password
        

        MaintenanceWorker worker = workerRepository.findByEmail(email);

        Map<String, String> response = new HashMap<>();

        if (worker != null && worker.getPassword().equals(password)) {
            response.put("message", "Worker login successful!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Invalid email or password.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
