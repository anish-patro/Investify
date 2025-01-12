package com.groww.anish.stocks_portfolio.dto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private Integer status;
    private T data;
}



