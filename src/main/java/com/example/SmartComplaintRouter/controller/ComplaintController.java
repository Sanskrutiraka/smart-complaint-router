package com.example.SmartComplaintRouter.controller;

import com.example.SmartComplaintRouter.model.Complaint;
import com.example.SmartComplaintRouter.model.User;
import com.example.SmartComplaintRouter.service.ComplaintService;
import com.example.SmartComplaintRouter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(
	    origins = "http://localhost:8086",
	    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
	)
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private UserService userService; // ✅ REQUIRED

    // ✅ Submit complaint WITH logged-in user
    @PostMapping("/submit")
    public Complaint submitComplaint(
            @RequestBody Complaint complaint,
            @RequestParam String email
    ) {
        System.out.println("EMAIL FROM REQUEST = " + email);

        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Attach ONLY user ID (clean & safe)
        User refUser = new User();
        refUser.setId(user.getId());
        complaint.setUser(refUser);

        return complaintService.submitComplaint(complaint);
    }


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
    
    // Get complaints of a specific user
    @GetMapping("/user/{userId}")
    public List<Complaint> getComplaintsByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        return complaintService.getComplaintsByUser(user);
    }
    
    @PutMapping("/{id}/status")
    public Complaint updateComplaintStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return complaintService.updateStatus(id, status);
    }
    
    // ADMIN: Get complaints by status
    @GetMapping("/status/{status}")
    public List<Complaint> getComplaintsByStatus(@PathVariable String status) {
        return complaintService.getComplaintsByStatus(status);
    }
    
    



}
