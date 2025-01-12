package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.ApiResponse;
import com.groww.anish.stocks_portfolio.dto.PortfolioResponseDTO;

public interface PortfolioService {

    public PortfolioResponseDTO getPortfolio(Long userId);
}
