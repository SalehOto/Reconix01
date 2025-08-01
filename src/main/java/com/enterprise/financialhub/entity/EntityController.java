package com.enterprise.financialhub.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
public class EntityController {
    
    private final EntityManagementService entityManagementService;
    
    @PostMapping
    public Map<String, Object> createOrUpdateEntity(@RequestBody Map<String, Object> entityData) {
        return entityManagementService.createOrUpdateEntity(entityData);
    }
    
    @GetMapping("/{entityId}")
    public Map<String, Object> getEntity(@PathVariable String entityId) {
        return entityManagementService.getEntity(entityId);
    }
    
    @PostMapping("/golden-record")
    public Map<String, Object> createGoldenRecord(@RequestBody Map<String, Object> request) {
        return entityManagementService.createGoldenRecord(request);
    }
    
    @GetMapping("/search")
    public Map<String, Object> searchEntities(@RequestParam Map<String, Object> searchCriteria) {
        return entityManagementService.searchEntities(searchCriteria);
    }
}