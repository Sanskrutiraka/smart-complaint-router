package com.example.SmartComplaintRouter.service;

import com.example.SmartComplaintRouter.model.Subject;
import com.example.SmartComplaintRouter.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    // ✅ Add new subject
    public Subject addSubject(String name) {

        if (name == null || name.isBlank()) {
            throw new RuntimeException("Subject name cannot be empty");
        }

        if (subjectRepository.existsByName(name.trim())) {
            throw new RuntimeException("Subject already exists");
        }

        Subject subject = new Subject(name.trim());
        return subjectRepository.save(subject);
    }

    // ✅ Get all subjects (admin view)
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    // ✅ Only active subjects (user dropdown later)
    public List<Subject> getActiveSubjects() {
        return subjectRepository.findByActiveTrue();
    }
}
