package com.enterprise.financialhub.anomaly;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/anomaly")
@RequiredArgsConstructor
public class AnomalyController {
    
    private final AnomalyDetectionService anomalyDetectionService;
    
    @PostMapping("/analyze")
    public Map<String, Object> analyzeTransaction(@RequestBody Map<String, Object> transaction) {
        return anomalyDetectionService.analyzeTransaction(transaction);
    }
    
    @GetMapping("/alerts")
    public Map<String, Object> getActiveAlerts() {
        return anomalyDetectionService.getActiveAlerts();
    }
    
    @PutMapping("/alerts/{alertId}")
    public Map<String, Object> updateAlertStatus(@PathVariable String alertId, 
                                                @RequestBody Map<String, Object> updateRequest) {
        return anomalyDetectionService.updateAlertStatus(alertId, updateRequest);
    }
}