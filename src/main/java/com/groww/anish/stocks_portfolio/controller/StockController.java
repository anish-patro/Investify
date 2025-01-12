package com.groww.anish.stocks_portfolio.controller;

import com.groww.anish.stocks_portfolio.dto.StocksDTO;
import com.groww.anish.stocks_portfolio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping()
    public ResponseEntity<List<StocksDTO>> createStocks(@RequestParam("file") MultipartFile file) {
        List<StocksDTO> updatedStocks = stockService.processCsvFile(file);
        return ResponseEntity.ok(updatedStocks);
    }

    @GetMapping
    public ResponseEntity<List<StocksDTO>> getStocks() {
        List<StocksDTO> stockDTOs = stockService.getAllStocks();
        return ResponseEntity.ok(stockDTOs);
    }
}
