package com.groww.anish.stocks_portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioResponseDTO {
    private List<PortfolioStockDTO> holdings;
    private Double totalPortfolioHolding;
    private Double totalBuyPrice;
    private Double totalProfitLoss;
    private Double totalPLPercentage;

}
