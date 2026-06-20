package com.example.SmartComplaintRouter.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ComplaintStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    private String oldStatus;
    private String newStatus;
    private String changedBy;

    private LocalDateTime changedAt;

    // ===== Getters & Setters =====

    public Long getId() { return id; }

    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public String getOldStatus() { return oldStatus; }
    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() { return newStatus; }
    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getChangedBy() { return changedBy; }
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public LocalDateTime getChangedAt() { return changedAt; }
    public void setChangedAt(LocalDateTime changedAt) {
        this.changedAt = changedAt;
    }
}
