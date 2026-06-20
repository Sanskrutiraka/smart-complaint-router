package com.example.SmartComplaintRouter.repository;

import com.example.SmartComplaintRouter.model.ComplaintCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Long> {

    List<ComplaintCategory> findByActiveTrue();

    boolean existsByNameIgnoreCase(String name);
}
