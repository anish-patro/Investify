package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.UserDTO;

import java.util.List;

public interface UserService {

    public UserDTO createUser(UserDTO userDTO);
    public List<UserDTO> getUsers();
    public UserDTO getUserById(Long id);
}
