package com.example.ConverterApi.Services;


import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import com.example.ConverterApi.enums.Category;
import org.springframework.stereotype.Service;
import com.example.ConverterApi.Interfaces.ConversionStrategy;

import java.util.Map;

@Service
public class ConversionService {

    private final Map<Category, ConversionStrategy> strategyMap;

    public ConversionService(TemperatureService temperatureService, WeightService weightService,  TimeService timeService) {
        strategyMap = Map.of(
                Category.TEMPERATURE, temperatureService,
                Category.WEIGHT, weightService,
                Category.TIME, timeService
        );
    }

    public ConversionResponse convert(ConversionRequest request) {
        ConversionStrategy strategy = strategyMap.get(request.getCategory());
        if (strategy == null) {
            throw new IllegalArgumentException("Invalid category: " + request.getCategory());
        }
        return strategy.convert(request);
    }
}

