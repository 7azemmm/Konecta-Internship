package com.example.ConverterApi.Models;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response model for unit conversion")
public class ConversionResponse {
    @Schema(description = "Converted value", example = "77.0")
    public double result;
    
    @Schema(description = "Conversion formula used", example = " tempDegree× 9/5 + 32")
    public String formula;
    
    @Schema(description = "Status of the conversion", example = "success")
    public String status;

}
