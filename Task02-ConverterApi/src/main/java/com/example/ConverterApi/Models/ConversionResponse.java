package com.example.ConverterApi.Models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponse {
    public double result;
    public String formula;
    public String status;

}
