package com.example.miniproject.Controles;

import com.example.miniproject.DTO.UserDTO;
import com.example.miniproject.Services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API pour l'authentification des utilisateurs")
public class AuthController {

    private final AuthService authService;

    public AuthController(@Lazy  AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "Inscrire un utilisateur", description = "Crée un nouvel utilisateur")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = authService.registerUser(userDTO);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Authentifie un utilisateur et retourne un token JWT")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        String token = authService.login(userDTO);
        return ResponseEntity.ok(token);
    }
}