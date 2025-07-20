package com.example.demo.Repository;

import com.example.demo.Entity.BillReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillReminderRepository extends JpaRepository<BillReminder, Long> {
    
    List<BillReminder> findByUserEmail(String userEmail);
    
    Optional<BillReminder> findByIdAndUserEmail(Long id, String userEmail);
    
    @Query("SELECT br FROM BillReminder br WHERE br.dueDate = :date")
    List<BillReminder> findByDueDate(@Param("date") LocalDate date);
    
    @Query("SELECT br FROM BillReminder br WHERE br.dueDate <= :date AND br.userEmail = :userEmail")
    List<BillReminder> findOverdueRemindersByUser(@Param("date") LocalDate date, @Param("userEmail") String userEmail);
}