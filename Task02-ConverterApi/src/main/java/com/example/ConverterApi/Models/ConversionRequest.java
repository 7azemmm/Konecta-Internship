package com.example.ConverterApi.Models;

import com.example.ConverterApi.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request model for unit conversion")
public class ConversionRequest {

    @NotNull(message = "Category must be provided")
    @Schema(description = "Conversion category (TEMPERATURE, WEIGHT, or TIME)", example = "TEMPERATURE")
    private Category category;

    @NotBlank(message = "the unit you need to convert from must be provided")
    @Schema(description = "Source unit for conversion", example = "CELSIUS")
    private String fromUnit;

    @NotBlank(message = "the unit you need to convert To must be provided")
    @Schema(description = "Target unit for conversion", example = "FAHRENHEIT")
    private String toUnit;

    @Positive(message = "Value must be greater than 0")
    @Schema(description = "Value to convert", example = "25.0", minimum = "0.000001")
    private double value;
}
