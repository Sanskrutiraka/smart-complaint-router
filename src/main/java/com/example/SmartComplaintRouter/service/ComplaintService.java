package com.example.SmartComplaintRouter.service;

import com.example.SmartComplaintRouter.model.Complaint;
import com.example.SmartComplaintRouter.model.RoutingLog;
import com.example.SmartComplaintRouter.model.User;
import com.example.SmartComplaintRouter.repository.ComplaintRepository;
import com.example.SmartComplaintRouter.repository.RoutingLogRepository;
import com.example.SmartComplaintRouter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private RoutingLogRepository routingLogRepository;
    
    @Autowired
    private UserRepository userRepository;


    // Save complaint and generate routing log
    public Complaint submitComplaint(Complaint complaint) {

        if (complaint.getUser() == null || complaint.getUser().getId() == null) {
            throw new RuntimeException("User is required");
        }

        Long userId = complaint.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        complaint.setUser(user);
        complaint.setSubmittedAt(LocalDateTime.now());
        complaint.setStatus("pending");

        Complaint savedComplaint = complaintRepository.save(complaint);

        RoutingLog log = new RoutingLog();
        log.setComplaint(savedComplaint);
        log.setPredictedCategory(predictCategory(complaint.getDescription()));
        log.setConfidenceScore(Math.random());
        log.setRoutedAt(LocalDateTime.now());

        routingLogRepository.save(log);

        return savedComplaint;
    }


    // Simulated AI prediction
    private String predictCategory(String description) {
        if (description.toLowerCase().contains("water")) return "Water";
        if (description.toLowerCase().contains("road")) return "Roads";
        if (description.toLowerCase().contains("garbage")) return "Sanitation";
        return "General";
    }

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }
    
    // Get complaints for a specific user
    public List<Complaint> getComplaintsByUser(User user) {
        return complaintRepository.findByUser(user);
    }
    
    public Complaint updateStatus(Long complaintId, String status) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(status);
        return complaintRepository.save(complaint);
    }
    
    public List<Complaint> getComplaintsByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }
    
    



}