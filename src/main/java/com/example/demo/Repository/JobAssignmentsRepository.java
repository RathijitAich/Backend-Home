package com.example.demo.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.Entity.JobAssignment;

@Repository
public interface JobAssignmentsRepository extends org.springframework.data.jpa.repository.JpaRepository<com.example.demo.Entity.JobAssignment, Long> {
    
    java.util.List<com.example.demo.Entity.JobAssignment> findByWorkerEmail(String workerEmail);

    List<JobAssignment> findByHomeownerEmail(String email);

}
