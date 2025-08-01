package com.enterprise.financialhub.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "golden_records", indexes = {
    @Index(name = "idx_golden_tenant_type", columnList = "tenant_id, entity_type"),
    @Index(name = "idx_golden_confidence", columnList = "confidence_score")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoldenRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String goldenRecordId;
    
    @Column(nullable = false, length = 100)
    private String tenantId;
    
    @Enumerated(EnumType.STRING)
    private EntityRecord.EntityType entityType;
    
    @Column(length = 200)
    private String masterName;
    
    @ElementCollection
    @CollectionTable(name = "golden_record_attributes", 
                     joinColumns = @JoinColumn(name = "golden_record_id"))
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value", length = 1000)
    @Builder.Default
    private Map<String, String> consolidatedAttributes = new HashMap<>();
    
    @ElementCollection
    @CollectionTable(name = "golden_record_sources", 
                     joinColumns = @JoinColumn(name = "golden_record_id"))
    @Column(name = "source_entity_id")
    @Builder.Default
    private List<String> sourceEntityIds = new ArrayList<>();
    
    private Integer confidenceScore;
    
    private Integer sourceCount;
    
    @Column(columnDefinition = "TEXT")
    private String mergingRules;
    
    @Column(columnDefinition = "TEXT")
    private String enrichmentSources;
    
    @Enumerated(EnumType.STRING)
    private GoldenRecordStatus status;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private LocalDateTime lastMergeAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = GoldenRecordStatus.ACTIVE;
        sourceCount = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public void addSourceEntity(String entityId) {
        if (!sourceEntityIds.contains(entityId)) {
            sourceEntityIds.add(entityId);
            sourceCount = sourceEntityIds.size();
        }
    }
    
    public void removeSourceEntity(String entityId) {
        sourceEntityIds.remove(entityId);
        sourceCount = sourceEntityIds.size();
    }
    
    public void addConsolidatedAttribute(String key, String value) {
        consolidatedAttributes.put(key, value);
    }
    
    public String getConsolidatedAttribute(String key) {
        return consolidatedAttributes.get(key);
    }
    
    public boolean hasConsolidatedAttribute(String key) {
        return consolidatedAttributes.containsKey(key);
    }
    
    public enum GoldenRecordStatus {
        ACTIVE, INACTIVE, PENDING_REVIEW, ARCHIVED
    }
}