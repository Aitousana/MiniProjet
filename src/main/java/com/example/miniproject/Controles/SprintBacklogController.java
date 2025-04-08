package com.example.miniproject.Controles;

import com.example.miniproject.DTO.SprintBacklogDTO;
import com.example.miniproject.DTO.SprintHistoryDTO;
import com.example.miniproject.Services.SprintBacklogService;
import com.example.miniproject.Services.SprintHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sprint-backlogs")
@Tag(name = "Sprint Backlogs", description = "API pour gérer les Sprint Backlogs")
public class SprintBacklogController {

    private final SprintBacklogService sprintBacklogService;
    private final SprintHistoryService sprintHistoryService;

    public SprintBacklogController(SprintBacklogService sprintBacklogService, SprintHistoryService sprintHistoryService) {
        this.sprintBacklogService = sprintBacklogService;
        this.sprintHistoryService = sprintHistoryService;
    }

    @PostMapping
    @Operation(summary = "Ajouter un Sprint Backlog", description = "Crée un nouveau Sprint Backlog")
    public ResponseEntity<SprintBacklogDTO> addSprintBacklog(@RequestBody SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklogDTO savedSprint = sprintBacklogService.addSprintBacklog(sprintBacklogDTO);
        return ResponseEntity.status(201).body(savedSprint);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un Sprint Backlog", description = "Met à jour un Sprint Backlog existant")
    public ResponseEntity<SprintBacklogDTO> updateSprintBacklog(@PathVariable int id, @RequestBody SprintBacklogDTO sprintBacklogDTO) {
        try {
            sprintBacklogDTO.setId(id);
            SprintBacklogDTO updatedSprint = sprintBacklogService.updateSprintBacklog(sprintBacklogDTO);
            return ResponseEntity.ok(updatedSprint);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un Sprint Backlog", description = "Supprime un Sprint Backlog par son ID")
    public ResponseEntity<Void> deleteSprintBacklog(@PathVariable int id) {
        try {
            sprintBacklogService.deleteSprintBacklog(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les Sprint Backlogs", description = "Retourne la liste de tous les Sprint Backlogs")
    public ResponseEntity<List<SprintBacklogDTO>> getAllSprintBacklogs() {
        List<SprintBacklogDTO> sprints = sprintBacklogService.getAllSprintBacklogs();
        return ResponseEntity.ok(sprints);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un Sprint Backlog par ID", description = "Retourne les détails d'un Sprint Backlog spécifique")
    public ResponseEntity<SprintBacklogDTO> getSprintBacklogById(@PathVariable int id) {
        try {
            SprintBacklogDTO sprintBacklogDTO = sprintBacklogService.getSprintBacklogById(id);
            return ResponseEntity.ok(sprintBacklogDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/{id}/log-progress")
    @Operation(summary = "Journaliser l'avancement d'un Sprint", description = "Ajoute une entrée dans l'historique du sprint")
    public ResponseEntity<Void> logSprintProgress(@PathVariable int id) {
        try {
            sprintHistoryService.logSprintProgress(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/{id}/burndown")
    @Operation(summary = "Récupérer le burndown chart d'un sprint", description = "Retourne l'historique pour le burndown chart")
    public ResponseEntity<List<SprintHistoryDTO>> getBurndownChart(@PathVariable int id) {
        try {
            List<SprintHistoryDTO> history = sprintHistoryService.getSprintHistory(id);
            return ResponseEntity.ok(history);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}