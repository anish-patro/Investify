package com.groww.anish.stocks_portfolio.service;

import com.groww.anish.stocks_portfolio.dto.UserDTO;
import com.groww.anish.stocks_portfolio.entity.User;
import com.groww.anish.stocks_portfolio.exception.ResourceNotFoundException;
import com.groww.anish.stocks_portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.modelmapper.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository uRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        uRepo.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = uRepo.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone()))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = uRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return new UserDTO(user.getId(), user.getName(), user.getName(), user.getEmail());
    }




}
