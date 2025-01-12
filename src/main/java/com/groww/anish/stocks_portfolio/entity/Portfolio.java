package com.groww.anish.stocks_portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioStock> portfolioStocks;

    @Column(name = "total_buy_price", nullable = false)
    private Double totalBuyPrice;

    @Column(name = "total_pl", nullable = false)
    private Double totalProfitLoss;

    @Column(name = "total_pl_percentage", nullable = false)
    private Double totalPLPercentage;

    @CreationTimestamp
    @Column(name = "instanceCreatedAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "instanceUpdatedAt")
    private LocalDateTime updatedAt;
}
