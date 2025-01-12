package com.groww.anish.stocks_portfolio.repository;

import com.groww.anish.stocks_portfolio.entity.Portfolio;
import com.groww.anish.stocks_portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUser(User user);
}
