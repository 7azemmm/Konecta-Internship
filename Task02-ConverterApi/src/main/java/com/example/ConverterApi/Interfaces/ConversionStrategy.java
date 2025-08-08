package com.example.ConverterApi.Interfaces;
import com.example.ConverterApi.enums.Category;
import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;

public interface ConversionStrategy {
        ConversionResponse convert(ConversionRequest request);
}
