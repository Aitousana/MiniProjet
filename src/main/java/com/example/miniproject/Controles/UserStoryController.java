package com.example.miniproject.Controles;

import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.Services.UserStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/api/user-stories")
@Tag(name = "User Stories", description = "API pour gérer les User Stories")
public class UserStoryController {

    private final UserStoryService userStoryService;

    @Autowired
    public UserStoryController(UserStoryService userStoryService) {
        this.userStoryService = userStoryService;
    }

    // Récupérer toutes les User Stories (non paginées)
    @GetMapping
    @Operation(summary = "Récupérer toutes les User Stories", description = "Retourne la liste complète des User Stories")
    public ResponseEntity<List<UserStoryDTO>> getAllUserStories() {
        List<UserStoryDTO> userStories = userStoryService.getUserStories();
        return ResponseEntity.ok(userStories);
    }

    // Récupérer toutes les User Stories (paginées)
    @GetMapping("/paginated")
    @Operation(summary = "Récupérer les User Stories paginées", description = "Retourne la liste paginée des User Stories")
    public ResponseEntity<Page<UserStoryDTO>> getPaginatedUserStories(Pageable pageable) {
        Page<UserStoryDTO> userStories = userStoryService.getUserStories(pageable);
        return ResponseEntity.ok(userStories);
    }

    // Récupérer une User Story par ID
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une User Story par ID", description = "Retourne les détails d'une User Story spécifique")
    public ResponseEntity<UserStoryDTO> getUserStoryById(@PathVariable int id) {
        UserStoryDTO userStory = userStoryService.getUserStoryById(id);
        return ResponseEntity.ok(userStory);
    }

    // Récupérer les User Stories par Epic ID
    @GetMapping("/by-epic/{epicId}")
    @Operation(summary = "Récupérer les User Stories par Epic ID", description = "Retourne les User Stories associées à un Epic")
    public ResponseEntity<List<UserStoryDTO>> getUserStoriesByEpicId(@PathVariable int epicId) {
        List<UserStoryDTO> userStories = userStoryService.getUserStoriesByEpicId(epicId);
        return ResponseEntity.ok(userStories);
    }

    // Créer une nouvelle User Story
    @PostMapping
    @Operation(summary = "Ajouter une User Story", description = "Crée une nouvelle User Story")
    public ResponseEntity<Void> createUserStory(@RequestBody UserStoryDTO userStoryDTO) {
        userStoryService.addUserStory(userStoryDTO);
        return ResponseEntity.status(201).build();
    }

    // Mettre à jour une User Story existante
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une User Story", description = "Met à jour une User Story existante")
    public ResponseEntity<Void> updateUserStory(@PathVariable int id, @RequestBody UserStoryDTO userStoryDTO) {
        userStoryDTO.setId(id); // Assure que l'ID est correctement défini
        userStoryService.updateUserStory(userStoryDTO);
        return ResponseEntity.ok().build();
    }

    // Supprimer une User Story
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une User Story", description = "Supprime une User Story par son ID")
    public ResponseEntity<Void> deleteUserStory(@PathVariable int id) {
        UserStoryDTO userStoryDTO = new UserStoryDTO();
        userStoryDTO.setId(id);
        userStoryService.deleteUserStory(userStoryDTO);
        return ResponseEntity.noContent().build();
    }

    // Supprimer plusieurs User Stories
    @DeleteMapping("/batch")
    @Operation(summary = "Supprimer plusieurs User Stories", description = "Supprime une liste de User Stories")
    public ResponseEntity<Void> deleteMultipleUserStories(@RequestBody List<UserStoryDTO> userStoryDTOList) {
        userStoryService.deleteAllUserStories(userStoryDTOList);
        return ResponseEntity.noContent().build();
    }
}