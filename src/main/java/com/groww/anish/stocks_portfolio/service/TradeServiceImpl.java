package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.TradeDTO;
import com.groww.anish.stocks_portfolio.dto.TradeResponseDTO;
import com.groww.anish.stocks_portfolio.entity.Portfolio;
import com.groww.anish.stocks_portfolio.entity.PortfolioStock;
import com.groww.anish.stocks_portfolio.entity.Stock;
import com.groww.anish.stocks_portfolio.entity.User;
import com.groww.anish.stocks_portfolio.repository.PortfolioRepository;
import com.groww.anish.stocks_portfolio.repository.PortfolioStockRepository;
import com.groww.anish.stocks_portfolio.repository.StockRepository;
import com.groww.anish.stocks_portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private StockRepository sRepo;

    @Autowired
    private PortfolioRepository pRepo;

    @Autowired
    private PortfolioStockRepository psRepo;

    @Autowired
    private UserRepository uRepo;

    @Transactional
    public TradeResponseDTO processTrade(TradeDTO tradeRequest) {
        TradeResponseDTO response = new TradeResponseDTO();

        Optional<Stock> stockOptional = sRepo.findById(tradeRequest.getStockId());
        if (stockOptional.isEmpty()) {
            response.setStatus("Failure");
            response.setMessage("Stock not found with ID: " + tradeRequest.getStockId());
            return response;
        }

        Optional<User> userOptional = uRepo.findById(tradeRequest.getUserId());
        if (userOptional.isEmpty()) {
            response.setStatus("Failure");
            response.setMessage("User not found with ID: " + tradeRequest.getUserId());
            return response;
        }

        Stock stock = stockOptional.get();
        User user = userOptional.get();

        Portfolio portfolio = pRepo.findByUser(user).orElseGet(() -> createNewPortfolio(user));

        if ("BUY".equalsIgnoreCase(tradeRequest.getTradeType())) {
            handleBuyTrade(portfolio, stock, tradeRequest.getQuantity(), stock.getClosePrice());
            response.setMessage("Successfully purchased");
            response.setStatus("Success");
        } else if ("SELL".equalsIgnoreCase(tradeRequest.getTradeType())) {
            String sellMessage = handleSellTrade(portfolio, stock, tradeRequest.getQuantity());
            response.setMessage(sellMessage);
            response.setStatus("Success");
        } else {
            response.setMessage("Invalid trade type. Please specify 'BUY' or 'SELL'.");
            response.setStatus("Failure");
        }

        return response;
    }

    @Transactional
    private void handleBuyTrade(Portfolio portfolio, Stock stock, Integer quantity, Double buyPrice) {
        PortfolioStock portfolioStock = psRepo.findByPortfolioAndStock(portfolio, stock)
                .orElseGet(() -> createNewPortfolioStock(portfolio, stock, quantity, buyPrice));

        int existingQuantity = portfolioStock.getQuantity();
        portfolioStock.setQuantity(existingQuantity + quantity);
        portfolioStock.setBuyPrice(((portfolioStock.getBuyPrice() * existingQuantity) + (buyPrice * quantity))
                / portfolioStock.getQuantity());

        psRepo.save(portfolioStock);

        portfolio.setTotalBuyPrice(Optional.ofNullable(portfolio.getTotalBuyPrice()).orElse(0.0) + (buyPrice * quantity));
        pRepo.save(portfolio);

        stock.setQuantity(stock.getQuantity() - quantity);
        sRepo.save(stock);
    }

    @Transactional
    private String handleSellTrade(Portfolio portfolio, Stock stock, Integer quantity) {
        PortfolioStock portfolioStock = psRepo.findByPortfolioAndStock(portfolio, stock)
                .orElse(null);

        if (portfolioStock == null || portfolioStock.getQuantity() < quantity) {
            return "Insufficient stock quantity to sell.";
        }

        Double gainLoss = (stock.getClosePrice() - portfolioStock.getBuyPrice()) * quantity;
        portfolioStock.setGainLoss(gainLoss);
        portfolioStock.setQuantity(portfolioStock.getQuantity() - quantity);
        if (portfolioStock.getQuantity() == 0) {
            psRepo.delete(portfolioStock);
        } else {
            psRepo.save(portfolioStock);
        }

        portfolio.setTotalBuyPrice(Optional.ofNullable(portfolio.getTotalBuyPrice()).orElse(0.0)
                - (portfolioStock.getBuyPrice() * quantity));
        portfolio.setTotalProfitLoss(Optional.ofNullable(portfolio.getTotalProfitLoss()).orElse(0.0) + gainLoss);
        pRepo.save(portfolio);

        // Update Stock quantity
        stock.setQuantity(stock.getQuantity() + quantity);
        sRepo.save(stock);

        return "Trade executed successfully.";
    }

    private Portfolio createNewPortfolio(User user) {
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setTotalBuyPrice(0.0);
        portfolio.setTotalProfitLoss(0.0);
        portfolio.setTotalPLPercentage(0.0);
        return pRepo.save(portfolio); // Persist the new Portfolio
    }

    public PortfolioStock createNewPortfolioStock(Portfolio portfolio, Stock stock, Integer quantity, Double buyPrice) {
        PortfolioStock portfolioStock = new PortfolioStock();
        portfolioStock.setPortfolio(portfolio);
        portfolioStock.setStock(stock);
        portfolioStock.setQuantity(quantity);
        portfolioStock.setBuyPrice(buyPrice);
        portfolioStock.setGainLoss(0.0); // Initialize gain/loss to 0

        return psRepo.save(portfolioStock);
    }
}
