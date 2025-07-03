package com.example.demo.Controller;

import com.example.demo.Entity.Review;
import com.example.demo.Entity.Homeowner;
import com.example.demo.Entity.MaintenanceWorker;
import com.example.demo.Entity.Job;
import com.example.demo.Repository.ReviewRepository;
import com.example.demo.Repository.HomeownerRepository;
import com.example.demo.Repository.WorkerRepository;
import com.example.demo.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private HomeownerRepository homeownerRepository;
    @Autowired
    private WorkerRepository maintenanceWorkerRepository;
    @Autowired
    private JobRepository jobRepository;

    @PostMapping
    public ResponseEntity<?> submitReview(@RequestBody Map<String, Object> reviewData) {
        String homeownerEmail = (String) reviewData.get("homeownerEmail");
        String workerEmail = (String) reviewData.get("workerEmail");
        String issueTitle = (String) reviewData.get("issueTitle");
        String reviewText = (String) reviewData.get("reviewText");
        Integer rating = (Integer) reviewData.get("rating");

        Map<String, String> response = new HashMap<>();

        // Validate required fields
        if (homeownerEmail == null || workerEmail == null || issueTitle == null || 
            reviewText == null || rating == null) {
            response.put("message", "Missing required review data.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Find entities
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

        Job job = jobRepository.findByIssueTitle(issueTitle);
        if (job == null) {
            response.put("message", "Job not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Check if review already exists
        if (reviewRepository.existsByHomeownerAndWorkerAndJob(homeowner, worker, job)) {
            response.put("message", "Review already exists for this job.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        // Create and save review
        Review review = new Review();
        review.setHomeowner(homeowner);
        review.setWorker(worker);
        review.setJob(job);
        review.setReviewText(reviewText);
        review.setRating(rating);

        reviewRepository.save(review);

        response.put("message", "Review submitted successfully!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/worker/{workerEmail}")
    public List<Review> getWorkerReviews(@PathVariable String workerEmail) {
        return reviewRepository.findByWorkerEmail(workerEmail);
    }

    @GetMapping("/homeowner/{homeownerEmail}")
    public List<Review> getHomeownerReviews(@PathVariable String homeownerEmail) {
        return reviewRepository.findByHomeownerEmail(homeownerEmail);
    }
}