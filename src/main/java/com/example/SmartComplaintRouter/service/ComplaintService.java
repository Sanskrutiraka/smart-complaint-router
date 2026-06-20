package com.example.SmartComplaintRouter.service;

import com.example.SmartComplaintRouter.model.Complaint;
import com.example.SmartComplaintRouter.model.ComplaintCategory;
import com.example.SmartComplaintRouter.model.ComplaintStatusHistory;
import com.example.SmartComplaintRouter.model.RoutingLog;
import com.example.SmartComplaintRouter.model.User;
import com.example.SmartComplaintRouter.repository.ComplaintCategoryRepository;
import com.example.SmartComplaintRouter.repository.ComplaintRepository;
import com.example.SmartComplaintRouter.repository.ComplaintStatusHistoryRepository;
import com.example.SmartComplaintRouter.repository.RoutingLogRepository;
import com.example.SmartComplaintRouter.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private RoutingLogRepository routingLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ComplaintCategoryRepository categoryRepository;

    @Autowired
    private DepartmentRoutingService routingService;

    @Autowired
    private ComplaintStatusHistoryRepository statusHistoryRepository;


    // ================= SUBMIT COMPLAINT =================
    public Complaint submitComplaint(
            String description,
            Long categoryId,
            MultipartFile image,
            Long userId) {

        if (userId == null) {
            throw new RuntimeException("User is required");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Complaint complaint = new Complaint();
        complaint.setDescription(description);
        complaint.setUser(user);
        complaint.setSubmittedAt(LocalDateTime.now());
        complaint.setStatus("pending");

        // ===== IMAGE SAVE =====
        String imageUrl = null;

        if (image != null && !image.isEmpty()) {
            try {

                String uploadDir =
                        System.getProperty("user.dir") + "/uploads";

                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName =
                        UUID.randomUUID() + "_" + image.getOriginalFilename();

                Path filePath = uploadPath.resolve(fileName);

                Files.copy(image.getInputStream(), filePath);

                imageUrl = "/uploads/" + fileName;

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Image upload failed");
            }
        }

        complaint.setImageUrl(imageUrl);

        // ===== CATEGORY + ROUTING =====
        if (categoryId != null) {

            ComplaintCategory category = categoryRepository
                    .findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Invalid category"));

            complaint.setCategory(category);

            String department =
                    routingService.resolveDepartment(category.getName());

            complaint.setAssignedDepartment(department);
        }

        Complaint savedComplaint = complaintRepository.save(complaint);

        // ===== ROUTING LOG =====
        RoutingLog log = new RoutingLog();
        log.setComplaint(savedComplaint);
        log.setPredictedCategory(predictCategory(description));
        log.setConfidenceScore(Math.random());
        log.setRoutedAt(LocalDateTime.now());

        routingLogRepository.save(log);

        return savedComplaint;
    }


    // ================= SIMULATED AI =================
    private String predictCategory(String description) {

        if (description == null) return "General";

        String text = description.toLowerCase();

        if (text.contains("network") || text.contains("internet"))
            return "Network";

        if (text.contains("printer") || text.contains("hardware"))
            return "Hardware";

        if (text.contains("software") || text.contains("install"))
            return "Software";

        return "General";
    }


    // ================= STATUS WORKFLOW VALIDATION =================
    private boolean isValidTransition(String currentStatus, String newStatus) {

        if (currentStatus == null || newStatus == null) return false;

        switch (currentStatus.toLowerCase()) {

            case "pending":
                return newStatus.equalsIgnoreCase("approved")
                        || newStatus.equalsIgnoreCase("rejected")
                        || newStatus.equalsIgnoreCase("resolved");

            case "approved":
                return newStatus.equalsIgnoreCase("rejected")
                        || newStatus.equalsIgnoreCase("resolved");

            case "rejected":
                return newStatus.equalsIgnoreCase("approved");

            case "resolved":
                return newStatus.equalsIgnoreCase("approved");

            default:
                return false;
        }
    }


    // ================= CRUD =================

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Optional<Complaint> getComplaintById(Long id) {
        return complaintRepository.findById(id);
    }

    public void deleteComplaint(Long id) {
        complaintRepository.deleteById(id);
    }

    public List<Complaint> getComplaintsByUser(User user) {
        return complaintRepository.findByUser(user);
    }


    // ================= STATUS UPDATE =================
    public Complaint updateStatus(Long complaintId, String newStatus, String changedBy) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        String currentStatus = complaint.getStatus();

        if (!isValidTransition(currentStatus, newStatus)) {
            throw new RuntimeException(
                    "Invalid status transition from "
                            + currentStatus + " to " + newStatus);
        }

        complaint.setStatus(newStatus);
        Complaint updatedComplaint = complaintRepository.save(complaint);

        ComplaintStatusHistory history = new ComplaintStatusHistory();
        history.setComplaint(updatedComplaint);
        history.setOldStatus(currentStatus);
        history.setNewStatus(newStatus);
        history.setChangedBy(changedBy);
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        return updatedComplaint;
    }

    public List<Complaint> getComplaintsByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }
}