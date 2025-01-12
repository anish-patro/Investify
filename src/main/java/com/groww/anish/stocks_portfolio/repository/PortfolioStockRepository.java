package com.groww.anish.stocks_portfolio.repository;

import com.groww.anish.stocks_portfolio.entity.Portfolio;
import com.groww.anish.stocks_portfolio.entity.PortfolioStock;
import com.groww.anish.stocks_portfolio.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioStockRepository extends JpaRepository<PortfolioStock, Long> {
    Optional<PortfolioStock> findByPortfolioAndStock(Portfolio portfolio, Stock stock);
}
