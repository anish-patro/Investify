package com.groww.anish.stocks_portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.*;

@SpringBootApplication
public class StocksPortfolioApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocksPortfolioApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
