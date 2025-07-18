package com.example.demo.Repository;

import com.example.demo.Entity.MaintenanceReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaintenanceReminderRepository extends JpaRepository<MaintenanceReminder, Long> {
    
    List<MaintenanceReminder> findByUserEmail(String userEmail);
    
    Optional<MaintenanceReminder> findByIdAndUserEmail(Long id, String userEmail);
    
    @Query("SELECT mr FROM MaintenanceReminder mr WHERE mr.nextDue = :date")
    List<MaintenanceReminder> findByNextDue(@Param("date") LocalDate date);
    
    @Query("SELECT mr FROM MaintenanceReminder mr WHERE mr.nextDue <= :date AND mr.userEmail = :userEmail")
    List<MaintenanceReminder> findOverdueMaintenanceByUser(@Param("date") LocalDate date, @Param("userEmail") String userEmail);
}