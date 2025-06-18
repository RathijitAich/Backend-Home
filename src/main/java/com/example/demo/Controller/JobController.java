
package com.example.demo.Controller;

import com.example.demo.Entity.Job;
import com.example.demo.Repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @GetMapping
    public List<Job> getAllJobs() {
        //write what data is returned by this endpoint
        
       // write the type of data returned by this endpoint
     

        return jobRepository.findAll();
    }
}