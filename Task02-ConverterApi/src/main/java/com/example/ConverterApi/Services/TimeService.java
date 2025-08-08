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
public class TimeService implements ConversionStrategy {

    private final Map<String, Function<Double, Double>> conversionsUnits = new HashMap<>();
    private final Map<String, String> formulas = new HashMap<>();

    public TimeService() {
        // Seconds to other units
        conversionsUnits.put("second-minute", timeUnit -> timeUnit / 60);
        formulas.put("second-minute", "timeUnit / 60");

        conversionsUnits.put("second-hour", timeUnit -> timeUnit / 3600);
        formulas.put("second-hour", "timeUnit / 3600");

        conversionsUnits.put("second-day", timeUnit -> timeUnit / 86400);
        formulas.put("second-day", "timeUnit / 86400");

        // Minutes to other units
        conversionsUnits.put("minute-second", timeUnit -> timeUnit * 60);
        formulas.put("minute-second", "timeUnit * 60");

        conversionsUnits.put("minute-hour", timeUnit -> timeUnit / 60);
        formulas.put("minute-hour", "timeUnit / 60");

        conversionsUnits.put("minute-day", timeUnit -> timeUnit / 1440);
        formulas.put("minute-day", "timeUnit / 1440");

        // Hours to other units
        conversionsUnits.put("hour-second", timeUnit -> timeUnit * 3600);
        formulas.put("hour-second", "timeUnit * 3600");

        conversionsUnits.put("hour-minute", timeUnit -> timeUnit * 60);
        formulas.put("hour-minute", "timeUnit * 60");

        conversionsUnits.put("hour-day", timeUnit -> timeUnit / 24);
        formulas.put("hour-day", "timeUnit / 24");

        // Days to other units
        conversionsUnits.put("day-second", timeUnit -> timeUnit * 86400);
        formulas.put("day-second", "timeUnit * 86400");

        conversionsUnits.put("day-minute", timeUnit -> timeUnit * 1440);
        formulas.put("day-minute", "timeUnit * 1440");

        conversionsUnits.put("day-hour", timeUnit -> timeUnit * 24);
        formulas.put("day-hour", "timeUnit * 24");
    }

    @Override
    public ConversionResponse convert(ConversionRequest request) {
        String key = request.getFromUnit().toLowerCase() + "-" + request.getToUnit().toLowerCase();

        if (!conversionsUnits.containsKey(key)) {
            throw new InvalidUnitException("Unsupported time conversion: " + key);
        }

        double result = conversionsUnits.get(key).apply(request.getValue());
        String formula = formulas.get(key);

        return new ConversionResponse(result, formula, "success");
    }
}
