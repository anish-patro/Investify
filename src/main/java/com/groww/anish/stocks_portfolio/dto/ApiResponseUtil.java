package com.groww.anish.stocks_portfolio.dto;

import org.springframework.stereotype.Component;

@Component
public class ApiResponseUtil {
    public static <T> ApiResponse<T> createApiResponse(boolean success, String message, Integer status, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setStatus(status);
        response.setData(data);
        return response;
    }
}
