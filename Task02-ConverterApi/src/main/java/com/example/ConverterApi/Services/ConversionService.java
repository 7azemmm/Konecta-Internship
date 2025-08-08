package com.example.ConverterApi.Services;


import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    private final TemperatureService temperatureService;
    private final WeightService weightService;

    public ConversionService(TemperatureService temperatureService , WeightService weightService) {
        this.temperatureService = temperatureService;
        this.weightService = weightService;
    }

    public ConversionResponse convertTemperature(ConversionRequest request) {
        return temperatureService.convert(request);
    }

    public ConversionResponse convertWeight(ConversionRequest request) {
        return weightService.convert(request);
    }

}
