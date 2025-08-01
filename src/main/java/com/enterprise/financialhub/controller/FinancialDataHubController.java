package com.enterprise.financialhub.controller;

import com.enterprise.financialhub.reconciliation.ReconciliationService;
import com.enterprise.financialhub.anomaly.AnomalyDetectionService;
import com.enterprise.financialhub.entity.EntityManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FinancialDataHubController {
    
    private final ReconciliationService reconciliationService;
    private final AnomalyDetectionService anomalyDetectionService;
    private final EntityManagementService entityManagementService;
    
    // Reconciliation endpoints
    @PostMapping("/reconcile")
    public Map<String, Object> startReconciliation(@RequestBody Map<String, Object> request) {
        return reconciliationService.startReconciliation(request);
    }
    
    @GetMapping("/reconcile/{jobId}")
    public Map<String, Object> getReconciliationStatus(@PathVariable String jobId) {
        return reconciliationService.getReconciliationStatus(jobId);
    }
    
    @GetMapping("/reconcile/{jobId}/matches")
    public Map<String, Object> getMatches(@PathVariable String jobId) {
        return reconciliationService.getMatches(jobId);
    }
    
    // Anomaly detection endpoints
    @PostMapping("/anomaly/analyze")
    public Map<String, Object> analyzeTransaction(@RequestBody Map<String, Object> transaction) {
        return anomalyDetectionService.analyzeTransaction(transaction);
    }
    
    @GetMapping("/anomaly/alerts")
    public Map<String, Object> getActiveAlerts() {
        return anomalyDetectionService.getActiveAlerts();
    }
    
    @PutMapping("/anomaly/alerts/{alertId}")
    public Map<String, Object> updateAlertStatus(@PathVariable String alertId, 
                                                @RequestBody Map<String, Object> updateRequest) {
        return anomalyDetectionService.updateAlertStatus(alertId, updateRequest);
    }
    
    // Entity management endpoints
    @PostMapping("/entities")
    public Map<String, Object> createOrUpdateEntity(@RequestBody Map<String, Object> entityData) {
        return entityManagementService.createOrUpdateEntity(entityData);
    }
    
    @GetMapping("/entities/{entityId}")
    public Map<String, Object> getEntity(@PathVariable String entityId) {
        return entityManagementService.getEntity(entityId);
    }
    
    @PostMapping("/entities/golden-record")
    public Map<String, Object> createGoldenRecord(@RequestBody Map<String, Object> request) {
        return entityManagementService.createGoldenRecord(request);
    }
    
    @GetMapping("/entities/search")
    public Map<String, Object> searchEntities(@RequestParam Map<String, Object> searchCriteria) {
        return entityManagementService.searchEntities(searchCriteria);
    }
}