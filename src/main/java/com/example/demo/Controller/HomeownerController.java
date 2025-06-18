package com.example.demo.Controller;

import com.example.demo.Entity.Homeowner;
import com.example.demo.Repository.HomeownerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/homeowner")
public class HomeownerController {

    @Autowired
    private HomeownerRepository homeownerRepository;

    @GetMapping("/{email}")
    public ResponseEntity<Homeowner> getHomeownerByEmail(@PathVariable String email) {
        Homeowner homeowner = homeownerRepository.findByEmail(email);
        if (homeowner != null) {
            return ResponseEntity.ok(homeowner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}