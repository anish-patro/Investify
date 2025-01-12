package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.TradeDTO;
import com.groww.anish.stocks_portfolio.dto.TradeResponseDTO;

public interface TradeService {
    public TradeResponseDTO processTrade(TradeDTO tradeRequest);
}
