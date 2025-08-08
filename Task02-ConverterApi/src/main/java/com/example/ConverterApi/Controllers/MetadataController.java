package com.example.ConverterApi.Controllers;

import com.example.ConverterApi.Services.MetadataService;
import com.example.ConverterApi.enums.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MetadataController {

    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping("/units")
    public ResponseEntity<?> getUnits(@RequestParam Category category) {
        var units = metadataService.getUnits(category);
        if (units == null) {
            return ResponseEntity.badRequest().body(
                    java.util.Map.of("error", "Unsupported category: " + category)
            );
        }
        return ResponseEntity.ok(units);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(metadataService.getCategories());
    }

    @GetMapping("/check-health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok(metadataService.getHealthStatus());
    }

    @GetMapping("/sample-payload")
    public ResponseEntity<?> getSample() {
        return ResponseEntity.ok(metadataService.getSample());
    }
}
