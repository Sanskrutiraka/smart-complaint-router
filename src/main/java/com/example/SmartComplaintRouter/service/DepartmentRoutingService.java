package com.example.SmartComplaintRouter.service;

import org.springframework.stereotype.Service;

@Service
public class DepartmentRoutingService {

    public String resolveDepartment(String categoryName) {

        if (categoryName == null) {
            return "General Support";
        }

        switch (categoryName.toLowerCase()) {

            case "it":
            case "technical":
                return "IT Support Department";

            case "hr":
            case "employee":
                return "HR Department";

            case "infrastructure":
            case "maintenance":
                return "Maintenance Department";

            case "finance":
                return "Finance Department";

            default:
                return "General Support";
        }
    }
}
