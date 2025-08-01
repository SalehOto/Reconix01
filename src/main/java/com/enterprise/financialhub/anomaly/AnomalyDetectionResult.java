package com.enterprise.financialhub.anomaly;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Data
@Builder
public class AnomalyDetectionResult {
    
    private String transactionId;
    
    private String tenantId;
    
    private boolean isAnomaly;
    
    private BigDecimal anomalyScore;
    
    private AnomalyAlert.AnomalyType primaryAnomalyType;
    
    private AnomalyAlert.AlertSeverity severity;
    
    @Builder.Default
    private Map<String, Object> detectionDetails = new HashMap<>();
    
    @Builder.Default
    private Map<String, BigDecimal> modelScores = new HashMap<>();
    
    private String detectionModel;
    
    private LocalDateTime detectionTime;
    
    private String riskProfile;
    
    private boolean requiresImmedateAction;
    
    private String recommendedAction;
    
    public void addModelScore(String modelName, BigDecimal score) {
        modelScores.put(modelName, score);
    }
    
    public void addDetectionDetail(String key, Object value) {
        detectionDetails.put(key, value);
    }
    
    public BigDecimal getModelScore(String modelName) {
        return modelScores.get(modelName);
    }
    
    public boolean hasModelScore(String modelName) {
        return modelScores.containsKey(modelName);
    }
}