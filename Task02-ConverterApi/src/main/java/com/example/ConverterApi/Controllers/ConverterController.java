package com.example.ConverterApi.Controllers;


import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import com.example.ConverterApi.Services.ConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@Tag(name = "Converter", description = "Unit conversion operations")
public class ConverterController {

    private final ConversionService conversionService;

    public ConverterController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @PostMapping("/convert")
    @Operation(
        summary = "Convert units",
        description = "Convert a value from one unit to another within the same category (temperature, weight, time). " +
                     "Temperature allows negative values, while weight and time require positive values. " +
                     "Units must match the specified category."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Conversion successful",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ConversionResponse.class),
                examples = @ExampleObject(
                    name = "Temperature Conversion",
                    value = "{\"result\": 32.0, \"formula\": \"tempDegree × 9/5 + 32\", \"status\": \"success\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Validation error - invalid units, category mismatch, or negative values for non-temperature categories",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Validation Error",
                    value = "{\"timestamp\": \"2024-01-01T12:00:00\", \"status\": 400, \"error\": \"Validation Error\", \"message\": \"Request validation failed\", \"errors\": {\"fromUnit\": \"From unit 'INVALID' is not valid for category 'TEMPERATURE'\", \"value\": \"Negative values are not allowed for category 'WEIGHT'\"}}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "422",
            description = "Invalid request format or missing required fields",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Missing Fields Error",
                    value = "{\"timestamp\": \"2024-01-01T12:00:00\", \"status\": 422, \"error\": \"Validation Error\", \"message\": \"Request validation failed\", \"errors\": {\"category\": \"Category must be provided\", \"fromUnit\": \"From unit must be provided\"}}"
                )
            )
        )
    })
    public ResponseEntity<ConversionResponse> convert(
        @Parameter(
            description = "Conversion request containing category, from and to units, and value",
            required = true,
            content = @Content(
                schema = @Schema(implementation = ConversionRequest.class),
                examples = @ExampleObject(
                    name = "Temperature Conversion Example",
                    value = "{\"category\": \"TEMPERATURE\", \"fromUnit\": \"CELSIUS\", \"toUnit\": \"FAHRENHEIT\", \"value\": 0.0}"
                )
            )
        )
        @RequestBody @Valid ConversionRequest request
    ) {
        ConversionResponse response = conversionService.convert(request);
        return ResponseEntity.ok(response);
    }
}







