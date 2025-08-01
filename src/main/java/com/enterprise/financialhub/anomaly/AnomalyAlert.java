package com.enterprise.financialhub.anomaly;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "anomaly_alerts", indexes = {
    @Index(name = "idx_alert_tenant_severity", columnList = "tenant_id, severity"),
    @Index(name = "idx_alert_status_created", columnList = "status, created_at"),
    @Index(name = "idx_alert_transaction", columnList = "transaction_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnomalyAlert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String alertId;
    
    @Column(nullable = false, length = 100)
    private String tenantId;
    
    @Column(nullable = false, length = 100)
    private String transactionId;
    
    @Enumerated(EnumType.STRING)
    private AnomalyType anomalyType;
    
    @Enumerated(EnumType.STRING)
    private AlertSeverity severity;
    
    @Enumerated(EnumType.STRING)
    private AlertStatus status;
    
    @Column(precision = 5, scale = 4)
    private BigDecimal anomalyScore;
    
    @Column(length = 500)
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String detectionDetails;
    
    @Column(length = 100)
    private String detectionModel;
    
    @Column(length = 100)
    private String assignedTo;
    
    private LocalDateTime assignedAt;
    
    @Column(length = 100)
    private String resolvedBy;
    
    private LocalDateTime resolvedAt;
    
    @Column(length = 1000)
    private String resolution;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = AlertStatus.OPEN;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum AnomalyType {
        FRAUD_PATTERN, BEHAVIORAL_ANOMALY, PROFILE_DEVIATION, 
        VELOCITY_ANOMALY, AMOUNT_ANOMALY, TIME_ANOMALY, 
        LOCATION_ANOMALY, MERCHANT_ANOMALY
    }
    
    public enum AlertSeverity {
        CRITICAL, HIGH, MEDIUM, LOW
    }
    
    public enum AlertStatus {
        OPEN, IN_PROGRESS, RESOLVED, CLOSED, FALSE_POSITIVE
    }
}