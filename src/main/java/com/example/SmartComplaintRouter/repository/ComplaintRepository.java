package com.example.SmartComplaintRouter.repository;

import com.example.SmartComplaintRouter.model.Complaint;
import com.example.SmartComplaintRouter.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
	// User-wise complaints
	 List<Complaint> findByUser(User user);
	 
	// Status-wise complaints (ADMIN use)
	 List<Complaint> findByStatus(String status);
	 
	 long countByStatus(String status);

	 @Query("SELECT COUNT(c) FROM Complaint c")
	 long countAllComplaints();
}