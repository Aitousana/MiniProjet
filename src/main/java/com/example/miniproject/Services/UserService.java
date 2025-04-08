package com.example.miniproject.Services;

import com.example.miniproject.DTO.UserDTO;
import com.example.miniproject.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO getUserById(int id);
    User loadUserByUsername(String username);
    void deleteUser(int id);
}