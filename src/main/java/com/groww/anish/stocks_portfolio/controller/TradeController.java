package com.groww.anish.stocks_portfolio.controller;

import com.groww.anish.stocks_portfolio.dto.TradeDTO;
import com.groww.anish.stocks_portfolio.dto.TradeResponseDTO;
import com.groww.anish.stocks_portfolio.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping()
    public ResponseEntity<TradeResponseDTO> tradeStock(@RequestBody TradeDTO tradeDTO){
        TradeResponseDTO response = tradeService.processTrade(tradeDTO);
        return ResponseEntity.ok(response);
    }

}
