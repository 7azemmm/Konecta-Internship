package com.example.ConverterApi.Controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import com.example.ConverterApi.enums.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class MetadataController {

    private static final Map<Category, List<String>> Units = Map.of(
            Category.TEMPERATURE, List.of("celsius", "fahrenheit", "kelvin"),
            Category.WEIGHT, List.of("gram", "kilogram", "pound", "ounce"),
            Category.TIME, List.of("second", "minute", "hour", "day")
    );

    @GetMapping("/units")
    public ResponseEntity<?> getUnits(@RequestParam Category category) {
        List<String> units = Units.get(category);
        if (units == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unsupported category: " + category));
        }
        return ResponseEntity.ok(units);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        if(Units.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No Categories available"));
        }
        return ResponseEntity.ok(Units.keySet());
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("status Unit Converter API is up and running");
    }

    @GetMapping("/sample")
    public ResponseEntity<Map<String ,Object>> getSample() {
        return ResponseEntity.ok(Map.of(
                "category", "TEMPERATURE",
                "fromUnit", "Celsius",
                "toUnit", "Fahrenheit",
                "value", 35,
                "note", "This is not part of the request body — just notes for you. " +
                        "Supported categories are:" +
                        "- TIME [day, minute, hour, second]" +
                        "- TEMPERATURE [Celsius, Fahrenheit, Kelvin]" +
                        "- WEIGHT [Gram, Kilogram, Pound, Ounce]" +
                        "Category is case sensitive but units are not." +
                        "No negative values supported for now."
        ));
    }

}
