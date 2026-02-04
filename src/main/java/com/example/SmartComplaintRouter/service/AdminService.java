package com.example.SmartComplaintRouter.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.SmartComplaintRouter.repository.ComplaintRepository;

@Service
public class AdminService {

    private final ComplaintRepository complaintRepository;

    public AdminService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("total", complaintRepository.countAllComplaints());
        stats.put("pending", complaintRepository.countByStatus("PENDING"));
        stats.put("approved", complaintRepository.countByStatus("APPROVED"));
        stats.put("resolved", complaintRepository.countByStatus("RESOLVED"));
        stats.put("rejected", complaintRepository.countByStatus("REJECTED"));

        return stats;
    }
}