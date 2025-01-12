package com.groww.anish.stocks_portfolio.controller;

import com.groww.anish.stocks_portfolio.dto.ApiResponse;
import com.groww.anish.stocks_portfolio.dto.PortfolioResponseDTO;
import com.groww.anish.stocks_portfolio.service.PortfolioServiceImpl;
import com.groww.anish.stocks_portfolio.dto.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioServiceImpl portfolioService;

    @Autowired
    private ApiResponseUtil apiResponseUtil;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<PortfolioResponseDTO>> getPortfolio(@PathVariable Long userId) {
        PortfolioResponseDTO portfolioResponse = portfolioService.getPortfolio(userId);

        if (portfolioResponse == null) {
            ApiResponse<PortfolioResponseDTO> response = apiResponseUtil.createApiResponse(false,
                    "Portfolio not found for user ID: " + userId, 404, null);
            return ResponseEntity.status(404).body(response);
        }

        ApiResponse<PortfolioResponseDTO> response = apiResponseUtil.createApiResponse(true,
                "Portfolio retrieved successfully", 200, portfolioResponse);
        return ResponseEntity.ok(response);
    }
}
