package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.StocksDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StockService {

    public List<StocksDTO> processCsvFile(MultipartFile file);
    public List<StocksDTO> getAllStocks();

}
