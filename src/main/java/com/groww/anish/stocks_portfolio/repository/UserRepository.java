package com.groww.anish.stocks_portfolio.repository;

import com.groww.anish.stocks_portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
