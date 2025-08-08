package com.example.ConverterApi.Services;

import com.example.ConverterApi.Exceptions.InvalidUnitException;
import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class TemperatureService  {

    private final Map<String, Function<Double, Double>> conversions = new HashMap<>();
    private final Map<String, String> formulas = new HashMap<>();

    public TemperatureService() {
        // Celsius to Fahrenheit and vice versa
        conversions.put("celsius-fahrenheit", tempDegree -> tempDegree * 9 / 5 + 32);
        formulas.put("celsius-fahrenheit", "tempDegree * 9 / 5 + 32");
        conversions.put("fahrenheit-celsius", tempDegree -> (tempDegree - 32) * 5 / 9);
        formulas.put("fahrenheit-celsius", "(tempDegree - 32) * 5 / 9");

        // Celsius to Kelvin and vice versa
        conversions.put("celsius-kelvin", tempDegree -> tempDegree + 273.15);
        formulas.put("celsius-kelvin" , "tempDegree + 273.15");
        conversions.put("kelvin-celsius", tempDegree -> tempDegree - 273.15);
        formulas.put("kelvin-celsius", "(tempDegree - 273.15)");

        // Fahrenheit to Kelvin and vise versa
        conversions.put("fahrenheit-kelvin", tempDegree -> (tempDegree - 32) * 5 / 9 + 273.15);
        formulas.put("fahrenheit-kelvin" , "(tempDegree - 32) * 5 / 9 + 273.15)");
        conversions.put("kelvin-fahrenheit", tempDegree -> (tempDegree - 273.15) * 9 / 5 + 32);
        formulas.put("kelvin-fahrenheit" , "(tempDegree - 32) * 9 / 5 + 32)");
    }


    public ConversionResponse convert(ConversionRequest request) {
        String key = request.getFromUnit().toLowerCase() + "-" + request.getToUnit().toLowerCase();


        if (!conversions.containsKey(key)) {
            throw new InvalidUnitException("Unsupported temperature conversion: " + key);
        }

        String formula = formulas.get(key);
        double result = conversions.get(key).apply(request.getValue());

        return new ConversionResponse(result ,formula ,"success");
    }
}
