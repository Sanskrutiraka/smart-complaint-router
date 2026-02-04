package com.example.SmartComplaintRouter.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 1000)
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime submittedAt;

    @Column(nullable = false)
    private String status; // "pending", "in_progress", "resolved"

    @OneToOne(mappedBy = "complaint", cascade = CascadeType.ALL)
    @JsonIgnore
    private RoutingLog routingLog;

    // Constructors
    public Complaint() {}

    public Complaint(User user, String description, String imageUrl,
                     LocalDateTime submittedAt, String status) {
        this.user = user;
        this.description = description;
        this.imageUrl = imageUrl;
        this.submittedAt = submittedAt;
        this.status = status;
    }

   
    @PrePersist
    public void onCreate() {
        if (this.submittedAt == null) {
            this.submittedAt = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = "pending";
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public RoutingLog getRoutingLog() { return routingLog; }
    public void setRoutingLog(RoutingLog routingLog) {
        this.routingLog = routingLog;
        routingLog.setComplaint(this);
    }
}
