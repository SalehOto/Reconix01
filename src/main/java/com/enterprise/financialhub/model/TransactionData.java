package com.enterprise.financialhub.model;

import lombok.Data;
import lombok.Builder;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Data
@Builder
public class TransactionData {
    @NotBlank
    private String transactionId;
    
    @NotBlank
    private String sourceSystem;
    
    @NotNull
    private LocalDateTime timestamp;
    
    private String rawData;
    
    @Builder.Default
    private Map<String, Object> normalizedFields = new HashMap<>();
    
    private String dataFormat; // JSON, XML, CSV, ISO20022, SWIFT_MT, SWIFT_MX
    
    private ValidationResult validationResult;
    
    @Data
    @Builder
    public static class ValidationResult {
        private boolean valid = true;
        private java.util.List<String> errors = new java.util.ArrayList<>();
        private java.util.List<String> warnings = new java.util.ArrayList<>();
        
        public void addError(String error) {
            errors.add(error);
            valid = false;
        }
        
        public void addWarning(String warning) {
            warnings.add(warning);
        }
    }
}