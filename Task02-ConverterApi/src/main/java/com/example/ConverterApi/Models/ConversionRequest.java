package com.example.ConverterApi.Models;

import com.example.ConverterApi.Validators.ValidConversionRequest;
import com.example.ConverterApi.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidConversionRequest
@Schema(description = "Request model for unit conversion")
public class ConversionRequest {

    @NotNull(message = "Category must be provided")
    @Schema(description = "Conversion category (TEMPERATURE, WEIGHT, or TIME)", example = "TEMPERATURE")
    private Category category;

    @NotBlank(message = "From unit must be provided")
    @Schema(description = "Source unit for conversion", example = "CELSIUS")
    private String fromUnit;

    @NotBlank(message = "To unit must be provided")
    @Schema(description = "Target unit for conversion", example = "FAHRENHEIT")
    private String toUnit;

    @Schema(description = "Value to convert (can be negative for temperature)", example = "25.0")
    private double value;
}
