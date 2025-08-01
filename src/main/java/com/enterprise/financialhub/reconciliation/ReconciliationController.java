package com.enterprise.financialhub.reconciliation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reconcile")
@RequiredArgsConstructor
public class ReconciliationController {
    
    private final ReconciliationService reconciliationService;
    
    @PostMapping
    public Map<String, Object> startReconciliation(@RequestBody Map<String, Object> request) {
        return reconciliationService.startReconciliation(request);
    }
    
    @GetMapping("/{jobId}")
    public Map<String, Object> getReconciliationStatus(@PathVariable String jobId) {
        return reconciliationService.getReconciliationStatus(jobId);
    }
    
    @GetMapping("/{jobId}/matches")
    public Map<String, Object> getMatches(@PathVariable String jobId) {
        return reconciliationService.getMatches(jobId);
    }
}