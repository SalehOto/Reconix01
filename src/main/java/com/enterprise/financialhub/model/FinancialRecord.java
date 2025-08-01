package com.enterprise.financialhub.model;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_records", indexes = {
    @Index(name = "idx_financial_tenant_date", columnList = "tenant_id, transaction_date"),
    @Index(name = "idx_financial_tenant_amount", columnList = "tenant_id, amount"),
    @Index(name = "idx_financial_reference", columnList = "reference")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    private String recordId;
    
    @Column(nullable = false, length = 100)
    @NotBlank
    private String tenantId;
    
    @Column(nullable = false, precision = 19, scale = 4)
    @NotNull
    private BigDecimal amount;
    
    @Column(nullable = false, length = 3)
    @NotBlank
    private String currency;
    
    @Column(nullable = false)
    @NotNull
    private LocalDate transactionDate;
    
    @Column(length = 200)
    private String reference;
    
    @Column(length = 500)
    private String description;
    
    @Column(length = 100)
    private String fromAccount;
    
    @Column(length = 100)
    private String toAccount;
    
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    
    @Enumerated(EnumType.STRING)
    private RecordStatus status;
    
    @Column(length = 50)
    private String batchId;
    
    @Column(length = 100)
    private String sourceSystem;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
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
    
    public enum TransactionType {
        CREDIT, DEBIT, TRANSFER, FEE, INTEREST, DIVIDEND, FOREX
    }
    
    public enum RecordStatus {
        PENDING, PROCESSED, FAILED, CANCELLED, RECONCILED
    }
}