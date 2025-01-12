package com.groww.anish.stocks_portfolio.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TradeDTO {
    private Long userId;
    private String tradeType;
    private Integer quantity;
    private Long stockId;
}
