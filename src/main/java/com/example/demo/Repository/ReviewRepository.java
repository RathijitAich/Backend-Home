package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Homeowner;
import com.example.demo.Entity.Job;
import com.example.demo.Entity.MaintenanceWorker;
import com.example.demo.Entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    boolean existsByHomeownerAndWorkerAndJob(Homeowner homeowner, MaintenanceWorker worker, Job job);
    
    Review save(Review review);
    
    List<Review> findByWorkerEmail(String workerEmail);
    
    List<Review> findByHomeownerEmail(String homeownerEmail);

    boolean existsByHomeownerAndJob(Homeowner homeowner, Job job);
}