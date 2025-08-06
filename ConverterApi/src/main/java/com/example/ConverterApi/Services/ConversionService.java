package com.example.ConverterApi.Services;


import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    private final TemperatureService temperatureService;

    public ConversionService(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    public ConversionResponse convertTemperature(ConversionRequest request) {
        return temperatureService.CelsiusToFahrenheit(request);
    }


}
