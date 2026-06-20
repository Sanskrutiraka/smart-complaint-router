package com.example.SmartComplaintRouter.repository;

import com.example.SmartComplaintRouter.model.ComplaintStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintStatusHistoryRepository
        extends JpaRepository<ComplaintStatusHistory, Long> {
}
