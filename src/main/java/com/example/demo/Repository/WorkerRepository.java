package com.example.demo.Repository;


import com.example.demo.Entity.MaintenanceWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<MaintenanceWorker, Long> {
    boolean existsByEmail(String email);
    MaintenanceWorker findByEmail(String email);
}