package com.groww.anish.stocks_portfolio.controller;

import com.groww.anish.stocks_portfolio.dto.UserDTO;
import com.groww.anish.stocks_portfolio.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService uService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@Validated @RequestBody UserDTO userDTO){
        UserDTO createdUser = uService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOs = uService.getUsers();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = uService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

}
