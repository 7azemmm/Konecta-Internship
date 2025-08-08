package com.example.ConverterApi.Validators;

import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.enums.Category;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class ConversionRequestValidator implements ConstraintValidator<ValidConversionRequest, ConversionRequest> {

    private static final Map<Category, Set<String>> units = Map.of(
            Category.TEMPERATURE, Set.of("celsius", "fahrenheit", "kelvin", "CELSIUS", "FAHRENHEIT", "KELVIN"),
            Category.WEIGHT, Set.of("gram", "kilogram", "pound", "ounce", "GRAM", "KILOGRAM", "POUND", "OUNCE"),
            Category.TIME, Set.of("second", "minute", "hour", "day", "SECOND", "MINUTE", "HOUR", "DAY")
    );

    private static final Set<Category> negativeCategories = Set.of(Category.TEMPERATURE);

    @Override
    public boolean isValid(ConversionRequest request, ConstraintValidatorContext context) {
        if (request == null) {
            return true;
        }

        boolean isValid = true;
        context.disableDefaultConstraintViolation();


        if (request.getCategory() == null) {
            addConstraintViolation(context, "Category must be provided");
            isValid = false;
        }

        if (request.getCategory() != Category.TEMPERATURE
                && request.getCategory() != Category.WEIGHT
                && request.getCategory() != Category.TIME) {
            addConstraintViolation(context, "Category must be one of TEMPERATURE, WEIGHT, or TIME");
            isValid = false;
        }


        if (request.getFromUnit() == null ) {
            addConstraintViolation(context, "From unit must be provided");
            isValid = false;
        }

        if (request.getToUnit() == null) {
            addConstraintViolation(context, "To unit must be provided");
            isValid = false;
        }

        if (request.getCategory() != null && request.getFromUnit() != null && request.getToUnit() != null) {
            Set<String> categoryUnits = units.get(request.getCategory());
            if (categoryUnits != null) {
                if (!categoryUnits.contains(request.getFromUnit())) {
                    addConstraintViolation(context,
                        String.format("From unit '%s' is not valid for category '%s'",
                            request.getFromUnit(), request.getCategory()));
                    isValid = false;
                }
                if (!categoryUnits.contains(request.getToUnit())) {
                    addConstraintViolation(context,
                        String.format("To unit '%s' is not valid for category '%s'",
                            request.getToUnit(), request.getCategory()));
                    isValid = false;
                }
            }
        }

        if (request.getCategory() != null && !negativeCategories.contains(request.getCategory())) {
            if (request.getValue() < 0) {
                addConstraintViolation(context,
                    String.format("Negative values are not allowed for category '%s'", request.getCategory()));
                isValid = false;
            }
        }

        if (request.getFromUnit() != null && request.getToUnit() != null &&
            request.getFromUnit().equalsIgnoreCase(request.getToUnit())) {
            addConstraintViolation(context, "From unit and to unit must be different");
            isValid = false;
        }

        return isValid;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
}

