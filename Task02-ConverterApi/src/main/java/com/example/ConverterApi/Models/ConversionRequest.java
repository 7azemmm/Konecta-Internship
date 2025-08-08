package com.example.ConverterApi.Models;

import com.example.ConverterApi.enums.Category;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {

    @NotNull(message = "Category must be provided")
    private Category category;

    @NotBlank(message = "the unit you need to convert from must be provided")
    private String fromUnit;

    @NotBlank(message = "the unit you need to convert To must be provided")
    private String toUnit;

    @Positive(message = "Value must be greater than 0")
    private double value;
}
