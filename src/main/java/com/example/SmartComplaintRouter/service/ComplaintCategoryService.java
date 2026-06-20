package com.example.SmartComplaintRouter.service;

import com.example.SmartComplaintRouter.model.ComplaintCategory;
import com.example.SmartComplaintRouter.repository.ComplaintCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintCategoryService {

    private final ComplaintCategoryRepository repository;

    public ComplaintCategoryService(ComplaintCategoryRepository repository) {
        this.repository = repository;
    }

    // ================= ADD CATEGORY (ADMIN) =================
    public ComplaintCategory addCategory(String name) {

        if (repository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException("Category already exists");
        }

        ComplaintCategory category = new ComplaintCategory();
        category.setName(name);

        //make new category active by default
        category.setActive(true);

        return repository.save(category);
    }

    // ================= USER DROPDOWN =================
    public List<ComplaintCategory> getActiveCategories() {
        return repository.findByActiveTrue();
    }

    // ================= ADMIN VIEW =================
    public List<ComplaintCategory> getAllCategories() {
        return repository.findAll();
    }

    // ================= ENABLE / DISABLE =================
    public ComplaintCategory toggleCategory(Long id, boolean active) {

        ComplaintCategory category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setActive(active);

        return repository.save(category);
    }
}
