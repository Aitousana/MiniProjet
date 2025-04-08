package com.example.miniproject.Controles;

import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.Services.UserStoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-stories")
@Tag(name = "User Stories", description = "API pour gérer les User Stories")
public class UserStoryController {

    private final UserStoryService userStoryService;

    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping
    @Operation(summary = "Ajouter une User Story", description = "Crée une nouvelle User Story")
    public ResponseEntity<Void> addUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        try {
            userStoryService.addUserStory(userStoryDTO);
            return ResponseEntity.status(201).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une User Story", description = "Met à jour une User Story existante")
    public ResponseEntity<Void> updateUserStory(@PathVariable int id, @RequestBody UserStoryDTO userStoryDTO) {
        try {
            userStoryDTO.setId(id);
            userStoryService.updateUserStory(userStoryDTO);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une User Story", description = "Supprime une User Story par son ID")
    public ResponseEntity<Void> deleteUserStory(@PathVariable int id) {
        try {
            UserStoryDTO userStoryDTO = new UserStoryDTO();
            userStoryDTO.setId(id);
            userStoryService.deleteUserStory(userStoryDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les User Stories", description = "Retourne la liste paginée des User Stories")
    public ResponseEntity<Page<UserStoryDTO>> getUserStories(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        Page<UserStoryDTO> page = userStoryService.getUserStories(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une User Story par ID", description = "Retourne les détails d'une User Story spécifique")
    public ResponseEntity<UserStoryDTO> getUserStoryById(@PathVariable int id) {
        try {
            UserStoryDTO userStoryDTO = userStoryService.getUserStoryById(id);
            return ResponseEntity.ok(userStoryDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/bulk")
    @Operation(summary = "Supprimer plusieurs User Stories", description = "Supprime une liste de User Stories")
    public ResponseEntity<Void> deleteAllUserStories(@RequestBody List<UserStoryDTO> userStoryDTOs) {
        try {
            userStoryService.deleteAllUserStories(userStoryDTOs);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/epic/{epicId}")
    @Operation(summary = "Récupérer les User Stories par Epic ID", description = "Retourne les User Stories associées à un Epic")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByEpicId(@PathVariable int epicId) {
        List<UserStoryDTO> userStories = userStoryService.getUserStoriesByEpicId(epicId);
        return ResponseEntity.ok(userStories);
    }
}