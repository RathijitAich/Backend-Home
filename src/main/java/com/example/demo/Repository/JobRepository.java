package com.example.demo.Repository;
import com.example.demo.Entity.Job;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    // Custom query methods can be added here if needed
    List<Job> findByJobStatus(String jobStatus);
    
    // Find jobs by homeowner email
    List<Job> findByHomeownerEmail(String homeownerEmail);
    
    // Find jobs by worker email
    List<Job> findByWorkerEmail(String workerEmail);

    //Find all jobs 
    List<Job> findAll();

    Job findByIssueTitle(String issueTitle);
}