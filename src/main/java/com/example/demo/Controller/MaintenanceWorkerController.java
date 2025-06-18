
package com.example.demo.Controller;

import com.example.demo.Entity.MaintenanceWorker;
import com.example.demo.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maintenance-worker")
public class MaintenanceWorkerController {

    @Autowired
    private WorkerRepository maintenanceWorkerRepository;

    @GetMapping("/{email}")
    public ResponseEntity<MaintenanceWorker> getWorkerByEmail(@PathVariable String email) {
        MaintenanceWorker worker = maintenanceWorkerRepository.findByEmail(email);
        if (worker != null) {
            return ResponseEntity.ok(worker);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}