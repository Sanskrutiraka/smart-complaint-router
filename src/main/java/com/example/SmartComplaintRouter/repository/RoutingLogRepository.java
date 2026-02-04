package com.example.SmartComplaintRouter.repository;

import com.example.SmartComplaintRouter.model.RoutingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutingLogRepository extends JpaRepository<RoutingLog, Long> {
    // You can add custom queries later if needed
}