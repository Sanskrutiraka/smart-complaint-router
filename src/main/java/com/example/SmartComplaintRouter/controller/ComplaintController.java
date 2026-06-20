package com.example.SmartComplaintRouter.controller;

import com.example.SmartComplaintRouter.model.Complaint;
import com.example.SmartComplaintRouter.model.User;
import com.example.SmartComplaintRouter.service.ComplaintService;
import com.example.SmartComplaintRouter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*") // safer for deployment phase
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService;


    // ================= SUBMIT COMPLAINT =================
    @PostMapping(value = "/submit", consumes = "multipart/form-data")
    public Complaint submitComplaint(

            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestHeader("X-SESSION-TOKEN") String token
    ) {

        if (token == null || token.isBlank()) {
            throw new RuntimeException("Session expired. Please login again.");
        }

        User user = userService.getUserByToken(token);

        if (user == null) {
            throw new RuntimeException("Invalid session");
        }

        // Admin restriction
        if (!"user".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Admins cannot submit complaints");
        }

        return complaintService.submitComplaint(
                description,
                categoryId,
                image,
                user.getId()
        );
    }


    // ================= CRUD =================

    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    @GetMapping("/{id}")
    public Optional<Complaint> getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteComplaint(@PathVariable Long id) {
        complaintService.deleteComplaint(id);
    }

    @GetMapping("/user/{userId}")
    public List<Complaint> getComplaintsByUser(
            @PathVariable Long userId,
            @RequestHeader("X-SESSION-TOKEN") String token) {

        if (token == null || token.isBlank()) {
            throw new RuntimeException("Invalid session");
        }

        User loggedUser = userService.getUserByToken(token);

        // SECURITY: user can only see own complaints
        if (!loggedUser.getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }

        return complaintService.getComplaintsByUser(loggedUser);
    }


    // ================= STATUS =================

    @PutMapping("/update-status/{id}")
    public Complaint updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam String adminName) {

        return complaintService.updateStatus(id, status, adminName);
    }

    @GetMapping("/status/{status}")
    public List<Complaint> getComplaintsByStatus(@PathVariable String status) {
        return complaintService.getComplaintsByStatus(status);
    }
}