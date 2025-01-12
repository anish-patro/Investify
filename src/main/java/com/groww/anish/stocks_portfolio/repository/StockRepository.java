package com.groww.anish.stocks_portfolio.repository;

import com.groww.anish.stocks_portfolio.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByName(String stockName);
}
