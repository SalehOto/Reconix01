package com.enterprise.financialhub.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Entity
@Table(name = "entity_records", indexes = {
    @Index(name = "idx_entity_tenant_type", columnList = "tenant_id, entity_type"),
    @Index(name = "idx_entity_external_id", columnList = "external_id"),
    @Index(name = "idx_entity_golden_record", columnList = "golden_record_id")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String entityId;
    
    @Column(nullable = false, length = 100)
    private String tenantId;
    
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    
    @Column(length = 100)
    private String externalId;
    
    @Column(length = 200)
    private String name;
    
    @Column(length = 100)
    private String goldenRecordId;
    
    @Enumerated(EnumType.STRING)
    private EntityStatus status;
    
    @ElementCollection
    @CollectionTable(name = "entity_attributes", 
                     joinColumns = @JoinColumn(name = "entity_id"))
    @MapKeyColumn(name = "attribute_name")
    @Column(name = "attribute_value", length = 1000)
    @Builder.Default
    private Map<String, String> attributes = new HashMap<>();
    
    @Column(columnDefinition = "TEXT")
    private String enrichmentData;
    
    private Integer confidenceScore;
    
    @Column(length = 100)
    private String sourceSystem;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private LocalDateTime lastEnrichmentAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = EntityStatus.ACTIVE;
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }
    
    public String getAttribute(String key) {
        return attributes.get(key);
    }
    
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }
    
    public enum EntityType {
        PERSON, ORGANIZATION, ACCOUNT, MERCHANT, BANK, COUNTERPARTY
    }
    
    public enum EntityStatus {
        ACTIVE, INACTIVE, MERGED, DUPLICATE, PENDING_REVIEW
    }
}