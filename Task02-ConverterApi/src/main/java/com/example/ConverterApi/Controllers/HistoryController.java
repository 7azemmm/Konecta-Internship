package com.example.ConverterApi.Controllers;


import com.example.ConverterApi.Models.ConversionResponse;
import com.example.ConverterApi.Services.HistoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/history/json")
    public List<ConversionResponse> historyJson(HttpSession session) {
        return historyService.printHistoryJson(session);
    }

    @GetMapping("/history/csv")
    public ResponseEntity<String> historyCsv(HttpSession session) {
        return historyService.printHistoryCsv(session);
    }

}
