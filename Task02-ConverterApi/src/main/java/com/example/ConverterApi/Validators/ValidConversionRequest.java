package com.example.ConverterApi.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConversionRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidConversionRequest {
    String message() default "not proper conversion request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
