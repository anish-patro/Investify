package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.StocksDTO;
import com.groww.anish.stocks_portfolio.entity.Stock;
import com.groww.anish.stocks_portfolio.repository.StockRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository sRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<StocksDTO> processCsvFile(MultipartFile file) {
        try {
            Iterable<CSVRecord> records = parseCsvFile(file);
            for (CSVRecord record : records) {
                StocksDTO stockDTO = mapCsvRecordToDTO(record);
                saveOrUpdateStock(stockDTO);
            }
            return getAllStocks();
        } catch (Exception e) {
            throw new RuntimeException("Failed to process CSV file: " + e.getMessage());
        }
    }

    private Iterable<CSVRecord> parseCsvFile(MultipartFile file) throws Exception {
        Reader in = new InputStreamReader(file.getInputStream());
        return CSVFormat.DEFAULT.withHeader().parse(in);
    }

    private StocksDTO mapCsvRecordToDTO(CSVRecord record) {
        StocksDTO stockDTO = new StocksDTO();
        stockDTO.setName(record.get("name"));
        stockDTO.setOpenPrice(Double.parseDouble(record.get("openPrice")));
        stockDTO.setClosePrice(Double.parseDouble(record.get("closePrice")));
        stockDTO.setHighPrice(Double.parseDouble(record.get("highPrice")));
        stockDTO.setLowPrice(Double.parseDouble(record.get("lowPrice")));
        stockDTO.setSettlementPrice(Double.parseDouble(record.get("settlementPrice")));
        stockDTO.setQuantity(Integer.parseInt(record.get("quantity")));
        return stockDTO;
    }

    private void saveOrUpdateStock(StocksDTO stockDTO) {
        Optional<Stock> existingStockOptional = sRepo.findByName(stockDTO.getName());
        Stock stock;

        if (existingStockOptional.isPresent()) {
            stock = existingStockOptional.get();

            modelMapper.map(stockDTO, stock);
        } else {
            stock = modelMapper.map(stockDTO, Stock.class);
        }

        sRepo.save(stock);
    }

    @Override
    public List<StocksDTO> getAllStocks() {
        List<Stock> stocks = sRepo.findAll();
        return stocks.stream()
                .map(stock -> modelMapper.map(stock, StocksDTO.class))
                .collect(Collectors.toList());
    }
}
