package com.example.ConverterApi.Controllers;

import com.example.ConverterApi.Models.ConversionResponse;
import com.example.ConverterApi.Services.MetadataService;
import com.example.ConverterApi.enums.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Metadata", description = "API metadata : health check , sample payload , categories , units")
public class MetadataController {

    private final MetadataService metadataService;

    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping("/units")
    @Operation(
        summary = "Get available units",
        description = "Retrieve all available units for a specific conversion category"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieved Units Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Temperature Units",
                                    value = "[\"Celsius\", \"Fahrenheit\", \"Kelvin\"]"
                            )
                    )
            )
    })

    public ResponseEntity<?> getUnits(
        @Parameter(
            description = "Conversion category (TEMPERATURE, WEIGHT, or TIME)",
            required = true,
            example = "TEMPERATURE"
        )
        @RequestParam Category category
    ) {
        var units = metadataService.getUnits(category);
        if (units == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Unsupported category: " + category)
            );
        }
        return ResponseEntity.ok(units);
    }

    @GetMapping("/categories")
    @Operation(
        summary = "Get available categories",
        description = "Retrieve all available conversion categories"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Retrieved Categories Successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Retrieve Categories",
                                    value = "[\"TEMPERATURE\", \"WEIGHT\", \"TIME\"]"
                            )
                    )
            )
    })
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(metadataService.getCategories());
    }

    @GetMapping("/check-health")
    @Operation(
        summary = "Health check",
        description = "Check the health status of the API"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Health-Check",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Health Check",
                                    value = "Healthy State for Converter Api"
                            )
                    )
            )
    })
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok(metadataService.getHealthStatus());
    }

    @GetMapping("/sample-payload")
    @Operation(
        summary = "Get sample payload",
        description = "Retrieve a sample conversion request payload for testing"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sample conversion request payload",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Sample ConversionRequest Payload",
                                    value = """
                {
                  "category": "WEIGHT",
                  "fromUnit": "GRAM",
                  "toUnit": "POND",
                  "value": 35
                }
                """
                            )
                    )
            )
    })
    public ResponseEntity<?> getSample() {
        return ResponseEntity.ok(metadataService.getSample());
    }
}
