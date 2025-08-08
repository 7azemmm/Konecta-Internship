package com.example.ConverterApi.Services;

import com.example.ConverterApi.Exceptions.InvalidUnitException;
import com.example.ConverterApi.Interfaces.ConversionStrategy;
import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class WeightService implements ConversionStrategy {

    private final Map<String, Function<Double, Double>> conversionsUnits = new HashMap<>();
    private final Map<String, String> formulas = new HashMap<>();

    public WeightService() {
        // Gram to Kilogram and vice versa
        conversionsUnits.put("gram-kilogram", weightUnit -> weightUnit / 1000);
        formulas.put("gram-kilogram", "weightUnit / 1000");
        conversionsUnits.put("kilogram-gram", weightUnit -> weightUnit * 1000);
        formulas.put("kilogram-gram", "weightUnit * 1000");

        // Gram to Pound and vice versa
        conversionsUnits.put("gram-pound", weightUnit -> weightUnit / 453.59237);
        formulas.put("gram-pound", "weightUnit / 453.59237");
        conversionsUnits.put("pound-gram", weightUnit -> weightUnit* 453.59237);
        formulas.put("pound-gram", "weightUnit* 453.59237");

        // Kilogram to Pound and vice versa
        conversionsUnits.put("kilogram-pound", weightUnit -> weightUnit * 2.20462);
        formulas.put("kilogram-pound", "weightUnit * 2.20462");
        conversionsUnits.put("pound-kilogram", weightUnit -> weightUnit / 2.20462);
        formulas.put("pound-kilogram", "weightUnit / 2.20462");

        // Ounce to Gram and vice versa
        conversionsUnits.put("ounce-gram", weightUnit -> weightUnit * 28.3495);
        formulas.put("ounce-gram", "weightUnit * 28.3495");
        conversionsUnits.put("gram-ounce", weightUnit -> weightUnit / 28.3495);
        formulas.put("gram-ounce", "weightUnit / 28.3495");

        // Ounce to Pound and vice versa
        conversionsUnits.put("ounce-pound", weightUnit -> weightUnit / 16);
        formulas.put("ounce-pound", "v / 16");
        conversionsUnits.put("pound-ounce", weightUnit -> weightUnit * 16);
        formulas.put("pound-ounce", "v * 16");
    }

    @Override
    public ConversionResponse convert(ConversionRequest request) {
        String key = request.getFromUnit().toLowerCase() + "-" + request.getToUnit().toLowerCase();

        if (!conversionsUnits.containsKey(key)) {
            throw new InvalidUnitException("Unsupported weight conversion: " + key);
        }

        double result = conversionsUnits.get(key).apply(request.getValue());
        String formula = formulas.get(key);

        return new ConversionResponse(result, formula, "success");
    }
}
