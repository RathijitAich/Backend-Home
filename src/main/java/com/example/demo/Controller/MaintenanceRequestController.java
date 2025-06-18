package com.example.demo.Controller;

import com.example.demo.Entity.Job;
import com.example.demo.Entity.Homeowner;
import com.example.demo.Entity.MaintenanceWorker;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Repository.HomeownerRepository;
import com.example.demo.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/maintenance-requests")
public class MaintenanceRequestController {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private WorkerRepository maintenanceWorkerRepository;

    @PostMapping
    public ResponseEntity<?> submitRequest(@RequestBody Map<String, Object> requestData) {
        try {
            // Extract emails if present
            String homeownerEmail = (String) requestData.get("homeowner_email");
            

            Homeowner homeowner = null;
            MaintenanceWorker worker = null;

            Job job = new Job();
            job.setServiceType((String) requestData.get("serviceType"));
            job.setRoomLocation((String) requestData.get("roomLocation"));
            job.setIssueTitle((String) requestData.get("issueTitle"));
            
            // check if issueTitle is unique
            if (jobRepository.findByIssueTitle(job.getIssueTitle()) != null) {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Issue title must be unique");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            job.setIssueDescription((String) requestData.get("issueDescription"));
            job.setPriorityLevel((String) requestData.get("priorityLevel"));
            job.setPreferredDate((String) requestData.get("preferredDate"));
            job.setPreferredTime((String) requestData.get("preferredTime"));
            job.setAccessInstructions((String) requestData.get("accessInstructions"));
            
            job.setJobStatus(String.valueOf(requestData.get("job_status")));
           
            homeownerEmail = (String) requestData.get("homeowner_email");

            if (homeownerEmail != null && !homeownerEmail.isEmpty()) {
                homeowner = homeownerRepository.findByEmail(homeownerEmail);
                if (homeowner == null) {
                    Map<String, String> error = new HashMap<>();
                    error.put("message", "Homeowner not found");
                    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                }
                job.setHomeowner(homeowner);
            }

            Job savedJob = jobRepository.save(job);

            return ResponseEntity.ok(savedJob);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Failed to submit maintenance request");
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}