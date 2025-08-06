package com.example.ConverterApi.Models;

import com.example.ConverterApi.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionRequest {
    private Category category;
    private String fromUnit;
    private String toUnit;
    private double value;
}
