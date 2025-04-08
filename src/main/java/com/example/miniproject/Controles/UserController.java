package com.example.miniproject.Controles;

import com.example.miniproject.DTO.UserDTO;
import com.example.miniproject.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "API pour gérer les utilisateurs")
public class UserController {

    private final UserService userService;

    public UserController(@Lazy UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un utilisateur par ID", description = "Retourne les détails d'un utilisateur")
    @PreAuthorize("hasRole('SCRUM_MASTER')")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un utilisateur", description = "Met à jour les détails d'un utilisateur")
    @PreAuthorize("hasRole('SCRUM_MASTER')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        try {
            userDTO.setId(id);
            UserDTO updatedUser = userService.registerUser(userDTO); // Réutilise register pour simplicité
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur par son ID")
    @PreAuthorize("hasRole('SCRUM_MASTER')")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        try {
            UserDTO userDTO = userService.getUserById(id);
            userService.deleteUser(id); // À ajouter dans UserService
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}