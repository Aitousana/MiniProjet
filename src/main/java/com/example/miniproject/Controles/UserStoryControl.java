package com.example.miniproject.Controles;

import com.example.miniproject.Services.UserStoryServiceImpl;
import com.example.miniproject.entities.UserStory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/UserStory")
public class UserStoryControl {

    private final UserStoryServiceImpl userStoryService;

    @Autowired
    public UserStoryControl(UserStoryServiceImpl userStoryService) {
        this.userStoryService = userStoryService;
    }

    @PostMapping("/Add")
    public ResponseEntity<String> addUserStory(@RequestBody UserStory userStory) {
        try {
            userStoryService.addUserStory(userStory);
            return ResponseEntity.ok("User Story ajoutée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de l'ajout de la User Story.");
        }
    }

    @PutMapping("/Update")
    public ResponseEntity<String> updateUserStory(@RequestBody UserStory userStory) {
        try {
            userStoryService.updateUserStory(userStory);
            return ResponseEntity.ok("User Story mise à jour avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la mise à jour de la User Story.");
        }
    }

    @DeleteMapping("/Delete")
    public ResponseEntity<String> deleteUserStory(@RequestBody UserStory userStory) {
        try {
            userStoryService.deleteUserStory(userStory);
            return ResponseEntity.ok("User Story supprimée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors de la suppression de la User Story.");
        }
    }
}
