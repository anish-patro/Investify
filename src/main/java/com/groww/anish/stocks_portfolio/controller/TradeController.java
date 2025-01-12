package com.groww.anish.stocks_portfolio.controller;

import com.groww.anish.stocks_portfolio.dto.ApiResponse;
import com.groww.anish.stocks_portfolio.dto.TradeDTO;
import com.groww.anish.stocks_portfolio.dto.TradeResponseDTO;
import com.groww.anish.stocks_portfolio.service.TradeService;
import com.groww.anish.stocks_portfolio.dto.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private ApiResponseUtil apiResponseUtil;

    @PostMapping()
    public ResponseEntity<ApiResponse<TradeResponseDTO>> tradeStock(@RequestBody TradeDTO tradeDTO) {
        TradeResponseDTO tradeResponse = tradeService.processTrade(tradeDTO);

        if ("Success".equalsIgnoreCase(tradeResponse.getStatus())) {
            return ResponseEntity.ok(apiResponseUtil.createApiResponse(true, tradeResponse.getMessage(), 200, tradeResponse));
        } else {
            return ResponseEntity.badRequest().body(apiResponseUtil.createApiResponse(false, tradeResponse.getMessage(), 400, null));
        }
    }
}
