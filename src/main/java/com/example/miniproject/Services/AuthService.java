package com.example.miniproject.Services;

import com.example.miniproject.DTO.UserDTO;

public interface AuthService {
    UserDTO registerUser(UserDTO userDTO);
    String login(UserDTO userDTO);
}