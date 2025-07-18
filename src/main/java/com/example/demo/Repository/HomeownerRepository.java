package com.example.demo.Repository;

import com.example.demo.Entity.Homeowner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeownerRepository extends JpaRepository<Homeowner, Long> {
    boolean existsByEmail(String email);
    Homeowner findByEmail(String email);
    
}
