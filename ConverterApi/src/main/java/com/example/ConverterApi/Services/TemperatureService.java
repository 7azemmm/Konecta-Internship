package com.example.ConverterApi.Services;

import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import org.springframework.stereotype.Service;

@Service
public class TemperatureService {

    private final ConversionResponse Response = new ConversionResponse();

    public TemperatureService() {
    }

    public ConversionResponse CelsiusToFahrenheit(ConversionRequest request){
        double degree = request.getValue();
        double celsius = degree * 9/5 + 32;
        Response.setResult(celsius);
        Response.setStatus("Success");
        return Response;

    }
}
