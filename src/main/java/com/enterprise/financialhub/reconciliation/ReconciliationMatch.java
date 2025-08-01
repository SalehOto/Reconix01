package com.enterprise.financialhub.reconciliation;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reconciliation_matches", indexes = {
    @Index(name = "idx_match_job_confidence", columnList = "reconciliation_job_id, confidence_score"),
    @Index(name = "idx_match_source_record", columnList = "source_record_id"),
    @Index(name = "idx_match_target_record", columnList = "target_record_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReconciliationMatch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reconciliation_job_id", nullable = false)
    private ReconciliationJob reconciliationJob;
    
    @Column(nullable = false, length = 100)
    private String sourceRecordId;
    
    @Column(length = 100)
    private String targetRecordId;
    
    @Enumerated(EnumType.STRING)
    private MatchType matchType;
    
    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;
    
    @Column(precision = 5, scale = 4)
    private BigDecimal confidenceScore;
    
    @Column(length = 500)
    private String matchingRules;
    
    @Column(length = 1000)
    private String matchDetails;
    
    @Column(length = 500)
    private String discrepancies;
    
    private Boolean requiresManualReview;
    
    @Column(length = 100)
    private String reviewedBy;
    
    private LocalDateTime reviewedAt;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        requiresManualReview = false;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum MatchType {
        EXACT, FUZZY, RULE_BASED, MANUAL
    }
    
    public enum MatchStatus {
        MATCHED, UNMATCHED, PARTIAL_MATCH, DISPUTED, APPROVED, REJECTED
    }
}