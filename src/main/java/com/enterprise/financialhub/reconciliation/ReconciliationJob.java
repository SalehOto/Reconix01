package com.enterprise.financialhub.reconciliation;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "reconciliation_jobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationJob {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String jobId;
    
    @Column(nullable = false, length = 100)
    private String tenantId;
    
    @Enumerated(EnumType.STRING)
    private JobStatus status;
    
    @Enumerated(EnumType.STRING)
    private ReconciliationType type;
    
    @Column(length = 100)
    private String sourceDataset;
    
    @Column(length = 100)
    private String targetDataset;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private Integer totalRecords;
    
    private Integer processedRecords;
    
    private Integer matchedRecords;
    
    private Integer unmatchedRecords;
    
    private Double matchRate;
    
    @Column(length = 1000)
    private String errorMessage;
    
    @OneToMany(mappedBy = "reconciliationJob", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<ReconciliationMatch> matches = new ArrayList<>();
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum JobStatus {
        PENDING, RUNNING, COMPLETED, FAILED, CANCELLED
    }
    
    public enum ReconciliationType {
        EXACT_MATCH, FUZZY_MATCH, RULE_BASED, COMPREHENSIVE
    }
}