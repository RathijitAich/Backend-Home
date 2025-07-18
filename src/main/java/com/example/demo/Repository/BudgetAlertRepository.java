package com.example.demo.Repository;

import com.example.demo.Entity.BudgetAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetAlertRepository extends JpaRepository<BudgetAlert, Long> {
    
    Optional<BudgetAlert> findByUserEmail(String userEmail);
    
    @Query("SELECT ba FROM BudgetAlert ba WHERE (ba.grocerySpent / ba.groceryBudget) >= 0.9 OR (ba.electricityEstimate / ba.electricityBudget) >= 0.9")
    List<BudgetAlert> findBudgetExceededUsers();
}