package com.example.demo.Controller;

import com.example.demo.Entity.Job;
import com.example.demo.Entity.JobAssignment;
import com.example.demo.Entity.Homeowner;
import com.example.demo.Entity.MaintenanceWorker;
import com.example.demo.Entity.Review;
import com.example.demo.Repository.JobAssignmentsRepository;
import com.example.demo.Repository.JobRepository;
import com.example.demo.Repository.HomeownerRepository;
import com.example.demo.Repository.WorkerRepository;
import com.example.demo.Repository.ReviewRepository;

import ch.qos.logback.core.util.SystemInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/job-applications")
public class JobApplicationController {

    @Autowired
    private JobAssignmentsRepository jobAssignmentsRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private WorkerRepository maintenanceWorkerRepository;
    @Autowired  
    private ReviewRepository reviewRepository;

    @PostMapping
    public ResponseEntity<?> applyForJob(@RequestBody Map<String, String> applicationData) {
        String issueTitle = applicationData.get("issueTitle");
        String homeownerEmail = applicationData.get("homeownerEmail");
        String workerEmail = applicationData.get("workerEmail");
        String status = applicationData.get("status");

        System.out.println("Received application data: " + applicationData);

        Map<String, String> response = new HashMap<>();

        // Validate required fields
        if (issueTitle == null || homeownerEmail == null || workerEmail == null || status == null) {
            response.put("message", "Missing required application data.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Job job = jobRepository.findByIssueTitle(issueTitle);
        if (job == null) {
            response.put("message", "Job not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Homeowner homeowner = homeownerRepository.findByEmail(homeownerEmail);
        if (homeowner == null) {
            response.put("message", "Homeowner not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        MaintenanceWorker worker = maintenanceWorkerRepository.findByEmail(workerEmail);
        if (worker == null) {
            response.put("message", "Worker not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Check if already applied
        if (!jobAssignmentsRepository.findByWorkerEmail(workerEmail).stream()
                .filter(a -> a.getJob().getIssueTitle().equals(issueTitle))
                .toList().isEmpty()) {
            response.put("message", "You have already applied for this job.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        JobAssignment assignment = new JobAssignment();
        assignment.setJob(job);
        assignment.setHomeowner(homeowner);
        assignment.setWorker(worker);
        assignment.setApplicationStatus(status);

        jobAssignmentsRepository.save(assignment);

        response.put("message", "Job application submitted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     @GetMapping("/homeowner/{email}")
    //return those that are not completed and pending
    public List<JobAssignment> getAssignmentsByHomeownerEmail(@PathVariable String email) {
        return jobAssignmentsRepository.findByHomeownerEmail(email).stream()
                .filter(a -> !"Completed".equalsIgnoreCase(a.getApplicationStatus()))
                .toList();
    }
    
    @GetMapping("/worker/{email}")
    public List<JobAssignment> getAssignmentsByWorkerEmail(@PathVariable String email) {
        return jobAssignmentsRepository.findByWorkerEmail(email);
    }

     @PutMapping("/update-status")
    public ResponseEntity<?> updateApplicationStatus(@RequestBody Map<String, String> requestData) {
        String issueTitle = requestData.get("issueTitle");
        String homeownerEmail = requestData.get("homeownerEmail");
        String workerEmail = requestData.get("workerEmail");
        String status = requestData.get("status");

        Map<String, String> response = new HashMap<>();

        if (issueTitle == null || homeownerEmail == null || workerEmail == null || status == null) {
            response.put("message", "Missing required data.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Find the assignment
        JobAssignment assignment = jobAssignmentsRepository.findByHomeownerEmail(homeownerEmail).stream()
                .filter(a -> a.getJob().getIssueTitle().equals(issueTitle)
                        && a.getWorker().getEmail().equals(workerEmail))
                .findFirst()
                .orElse(null);

        if (assignment == null) {
            response.put("message", "Job application not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        assignment.setApplicationStatus(status);

        // if status is accepted we should automatically make other applications for the same job rejected
        // this is for applications
        if ("Accepted".equalsIgnoreCase(status)) {
            List<JobAssignment> otherAssignments = jobAssignmentsRepository.findByHomeownerEmail(homeownerEmail).stream()
                    .filter(a -> a.getJob().getIssueTitle().equals(issueTitle) && !a.getWorker().getEmail().equals(workerEmail))
                    .toList();
            for (JobAssignment otherAssignment : otherAssignments) {
                otherAssignment.setApplicationStatus("Rejected");
            }
            jobAssignmentsRepository.saveAll(otherAssignments);
        }
       

        if("Completed".equalsIgnoreCase(status)) {
            // application status
            assignment.setApplicationStatus("Completed");

            // job status
            Job job = jobRepository.findByIssueTitle(issueTitle);
            if (job != null) {
                job.setJobStatus("Completed");
                jobRepository.save(job);
            } else {
                response.put("message", "Job not found for completion.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        jobAssignmentsRepository.save(assignment);




        //set the job status in progress
        if("Approved".equalsIgnoreCase(status)) {
            Job job = jobRepository.findByIssueTitle(issueTitle);
            if (job != null) {
                job.setJobStatus("In Progress");
                jobRepository.save(job);
            } else {
                response.put("message", "Job not found for updating status.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }

        response.put("message", "Application status updated successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   

    //fetch those completed jobs that have not been reviewed by the homeowner
    @GetMapping("/unreviewed-jobs/homeowner/{email}")
    public List<JobAssignment> getUnreviewedJobsByHomeownerEmail(@PathVariable String email) {
        List<JobAssignment> completedJobs = jobAssignmentsRepository.findByHomeownerEmail(email).stream()
                .filter(a -> "Completed".equalsIgnoreCase(a.getApplicationStatus()))
                .toList();

        // Filter out jobs that have already been reviewed
        return completedJobs.stream()
                .filter(assignment -> !reviewRepository.existsByHomeownerAndJob(assignment.getHomeowner(), assignment.getJob()))
                .toList();
    }

    
}