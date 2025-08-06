package com.example.ConverterApi.Controllers;


import com.example.ConverterApi.Models.ConversionRequest;
import com.example.ConverterApi.Models.ConversionResponse;
import com.example.ConverterApi.Services.ConversionService;
import com.example.ConverterApi.enums.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1")
public class ConverterController {

    private final ConversionService conversionService;

    public ConverterController(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(@RequestBody ConversionRequest request) {
        Category category = request.getCategory();

        ConversionResponse response;

        switch (category) {
            case TEMPERATURE -> response = conversionService.convertTemperature(request);
            default -> throw new IllegalArgumentException("Invalid category");
        }

        return ResponseEntity.ok(response);
    }



}
