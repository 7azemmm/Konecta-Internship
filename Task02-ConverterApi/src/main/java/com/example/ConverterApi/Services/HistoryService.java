package com.example.ConverterApi.Services;

import com.example.ConverterApi.Models.ConversionResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

@Service
public class HistoryService {


    public List<ConversionResponse> printHistoryJson(HttpSession session) {
        List<ConversionResponse> history = (List<ConversionResponse>) session.getAttribute("conversionHistory");
        return history != null ? history : Collections.emptyList();
    }

    public ResponseEntity<String> printHistoryCsv(HttpSession session) {
        List<ConversionResponse> history = (List<ConversionResponse>) session.getAttribute("conversionHistory");
        if (history == null) {
            history = Collections.emptyList();
        }

        StringBuilder csvFile = new StringBuilder();
        csvFile.append("Result,Formula,Status\n");
        for (ConversionResponse record : history) {
            csvFile.append(record.getResult()).append(",");
            csvFile.append("\"").append(record.getFormula().replace("\"", "\"\"")).append("\",");
            csvFile.append(record.getStatus()).append("\n");
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=conversion_history.csv")
                .header("Content-Type", "text/csv")
                .body(csvFile.toString());
    }


}
