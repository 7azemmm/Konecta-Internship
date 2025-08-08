package com.example.ConverterApi.Services;

import com.example.ConverterApi.enums.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MetadataService {

    private static final Map<Category, List<String>> UNITS = Map.of(
            Category.TEMPERATURE, List.of("celsius", "fahrenheit", "kelvin"),
            Category.WEIGHT, List.of("gram", "kilogram", "pound", "ounce"),
            Category.TIME, List.of("second", "minute", "hour", "day")
    );

    public List<String> getUnits(Category category) {
        return UNITS.get(category);
    }

    public List<Category> getCategories() {
        return List.copyOf(UNITS.keySet());
    }

    public Map<String, Object> getSample() {
        return Map.of(
                "category", "TEMPERATURE",
                "fromUnit", "Celsius",
                "toUnit", "Fahrenheit",
                "value", 35,
                "note", "This is not part of the request body" +
                        "Supported categories are: TIME [day, minute, hour, second], " +
                        "TEMPERATURE [Celsius, Fahrenheit, Kelvin], " +
                        "WEIGHT [Gram, Kilogram, Pound, Ounce]. " +
                        "Category is case sensitive but units are not. " +
                        "No negative values supported for now."
        );
    }

    public String getHealthStatus() {
        return "Healthy State for Converter Api";
    }
}
