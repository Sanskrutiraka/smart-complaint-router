package com.example.SmartComplaintRouter.repository;

import com.example.SmartComplaintRouter.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findByActiveTrue();

    boolean existsByName(String name);
}
