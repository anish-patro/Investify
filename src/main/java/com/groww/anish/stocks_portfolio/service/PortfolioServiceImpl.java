package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.PortfolioStockDTO;
import com.groww.anish.stocks_portfolio.dto.PortfolioResponseDTO;
import com.groww.anish.stocks_portfolio.entity.Portfolio;
import com.groww.anish.stocks_portfolio.entity.PortfolioStock;
import com.groww.anish.stocks_portfolio.entity.Stock;
import com.groww.anish.stocks_portfolio.entity.User;
import com.groww.anish.stocks_portfolio.repository.PortfolioRepository;
import com.groww.anish.stocks_portfolio.repository.PortfolioStockRepository;
import com.groww.anish.stocks_portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioStockRepository portfolioStockRepository;

    @Override
    public PortfolioResponseDTO getPortfolio(Long userId) {
        Optional<User> userOptional = uRepo.findById(userId);
        if (userOptional.isEmpty()) {
            return null; // or throw exception
        }

        User user = userOptional.get();
        Optional<Portfolio> portfolioOptional = portfolioRepository.findByUser(user);

        if (portfolioOptional.isEmpty()) {
            return null; // or throw exception
        }

        Portfolio portfolio = portfolioOptional.get();

        // Fetch Holdings
        List<PortfolioStockDTO> holdings = portfolioStockRepository.findByPortfolio(portfolio).stream()
                .map(portfolioStock -> {
                    Stock stock = portfolioStock.getStock();
                    PortfolioStockDTO holding = new PortfolioStockDTO(
                            stock.getName(),
                            stock.getId(),
                            portfolioStock.getQuantity(),
                            portfolioStock.getBuyPrice(),
                            stock.getClosePrice(),
                            (stock.getClosePrice() - portfolioStock.getBuyPrice()) * portfolioStock.getQuantity()
                    );
                    return holding;
                }).collect(Collectors.toList());

        // Calculate Total Portfolio Holding
        double totalPortfolioHolding = holdings.stream()
                .mapToDouble(h -> h.getCurrentPrice() * h.getQuantity())
                .sum();

        // Prepare Response Data
        PortfolioResponseDTO portfolioResponse = new PortfolioResponseDTO();
        portfolioResponse.setHoldings(holdings);
        portfolioResponse.setTotalPortfolioHolding(totalPortfolioHolding);
        portfolioResponse.setTotalBuyPrice(portfolio.getTotalBuyPrice());
        portfolioResponse.setTotalProfitLoss(portfolio.getTotalProfitLoss());
        portfolioResponse.setTotalPLPercentage(portfolio.getTotalBuyPrice() > 0 ?
                (portfolio.getTotalProfitLoss() / portfolio.getTotalBuyPrice()) * 100 : 0.0);

        return portfolioResponse;
    }
}
