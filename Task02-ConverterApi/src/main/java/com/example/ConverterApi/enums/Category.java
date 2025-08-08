package com.example.ConverterApi.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Conversion categories")
public enum Category {
        @Schema(description = "Temperature conversions (Celsius, Fahrenheit, Kelvin)")
        TEMPERATURE,
        @Schema(description = "Weight conversions (Kilograms, Pounds, Ounces, Grams)")
        WEIGHT,
        @Schema(description = "Time conversions (Seconds, Minutes, Hours, Days)")
        TIME
}
