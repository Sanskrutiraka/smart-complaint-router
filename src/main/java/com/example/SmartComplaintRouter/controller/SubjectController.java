package com.example.SmartComplaintRouter.controller;

import com.example.SmartComplaintRouter.model.Subject;
import com.example.SmartComplaintRouter.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8086")
@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    // ✅ ADMIN: Add new subject
    @PostMapping
    public Subject addSubject(@RequestParam String name) {
        return subjectService.addSubject(name);
    }

    // ✅ ADMIN: View all subjects
    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    // ✅ USER: Get active subjects only
    @GetMapping("/active")
    public List<Subject> getActiveSubjects() {
        return subjectService.getActiveSubjects();
    }
}
