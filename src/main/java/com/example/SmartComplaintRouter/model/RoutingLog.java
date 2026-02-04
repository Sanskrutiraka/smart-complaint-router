package com.example.SmartComplaintRouter.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "routing_logs")
public class RoutingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "complaint_id")
    @JsonIgnore
    private Complaint complaint;

    @Column(nullable = false)
    private String predictedCategory;

    @Column(nullable = false)
    private Double confidenceScore;

    private LocalDateTime routedAt;

    // Constructors
    public RoutingLog() {}

    public RoutingLog(Complaint complaint, String predictedCategory, Double confidenceScore, LocalDateTime routedAt) {
        this.complaint = complaint;
        this.predictedCategory = predictedCategory;
        this.confidenceScore = confidenceScore;
        this.routedAt = routedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Complaint getComplaint() { return complaint; }
    public void setComplaint(Complaint complaint) { this.complaint = complaint; }

    public String getPredictedCategory() { return predictedCategory; }
    public void setPredictedCategory(String predictedCategory) { this.predictedCategory = predictedCategory; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }

    public LocalDateTime getRoutedAt() { return routedAt; }
    public void setRoutedAt(LocalDateTime routedAt) { this.routedAt = routedAt; }
}