package com.example.crm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        return Map.of(
                "totalCustomers", 128,
                "openLeads", 42,
                "totalRevenue", 125400
        );
    }
}
