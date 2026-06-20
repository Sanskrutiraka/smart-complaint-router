package com.example.SmartComplaintRouter.controller;

import com.example.SmartComplaintRouter.model.ComplaintCategory;
import com.example.SmartComplaintRouter.service.ComplaintCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8086")
public class ComplaintCategoryController {

    private final ComplaintCategoryService service;

    public ComplaintCategoryController(ComplaintCategoryService service) {
        this.service = service;
    }

    // Admin: add category
    @PostMapping
    public ComplaintCategory addCategory(@RequestParam String name) {
        return service.addCategory(name);
    }

    // User/Admin: get active categories (for dropdown)
    @GetMapping("/active")
    public List<ComplaintCategory> getActiveCategories() {
        return service.getActiveCategories();
    }

    // Admin: get all
    @GetMapping
    public List<ComplaintCategory> getAllCategories() {
        return service.getAllCategories();
    }

    // Admin: enable/disable
    @PutMapping("/{id}/status")
    public ComplaintCategory updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {
        return service.toggleCategory(id, active);
    }
}
