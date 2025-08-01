package com.enterprise.financialhub.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HealthController {
    
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Map<String, Object> status = Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now(),
            "service", "Enterprise Financial Data Hub",
            "version", "1.0.0",
            "components", Map.of(
                "reconciliation", "UP",
                "anomalyDetection", "UP", 
                "entityManagement", "UP",
                "database", "UP"
            )
        );
        
        return ResponseEntity.ok(status);
    }
    
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> info = Map.of(
            "application", Map.of(
                "name", "Enterprise Financial Data Hub",
                "description", "Production-grade financial data ingestion and processing platform",
                "version", "1.0.0"
            ),
            "features", Map.of(
                "reconciliation", "Multi-phase matching algorithms",
                "anomalyDetection", "ML-powered fraud detection",
                "entityManagement", "Golden record management",
                "eventDriven", "Real-time Kafka processing"
            ),
            "architecture", Map.of(
                "framework", "Spring Boot 3.2",
                "java", "17",
                "database", "MySQL 8.0",
                "messaging", "Apache Kafka",
                "security", "OAuth2 + Vault"
            )
        );
        
        return ResponseEntity.ok(info);
    }
}