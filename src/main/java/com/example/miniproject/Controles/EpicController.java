package com.example.miniproject.Controles;

import com.example.miniproject.DTO.EpicDTO;
import com.example.miniproject.Services.EpicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epics")
@Tag(name = "Epics", description = "API pour gérer les Epics")
public class EpicController {

    private final EpicService epicService;

    public EpicController(EpicService epicService) {
        this.epicService = epicService;
    }

    @PostMapping
    @Operation(summary = "Ajouter un Epic", description = "Crée un nouvel Epic")
    public ResponseEntity<Void> addEpic(@RequestBody EpicDTO epicDTO) {
        try {
            epicService.addEpic(epicDTO);
            return ResponseEntity.status(201).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un Epic", description = "Met à jour les détails d'un Epic existant")
    public ResponseEntity<Void> updateEpic(@PathVariable int id, @RequestBody EpicDTO epicDTO) {
        try {
            epicDTO.setId(id);
            epicService.updateEpic(epicDTO);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un Epic", description = "Supprime un Epic par son ID")
    public ResponseEntity<Void> deleteEpic(@PathVariable int id) {
        try {
            EpicDTO epicDTO = new EpicDTO();
            epicDTO.setId(id);
            epicService.deleteEpic(epicDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les Epics", description = "Retourne la liste de tous les Epics")
    public ResponseEntity<List<EpicDTO>> getAllEpics() {
        List<EpicDTO> epics = epicService.getAllEpics();
        return ResponseEntity.ok(epics);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un Epic par ID", description = "Retourne les détails d'un Epic spécifique")
    public ResponseEntity<EpicDTO> getEpicById(@PathVariable int id) {
        try {
            EpicDTO epicDTO = epicService.getEpicById(id);
            return ResponseEntity.ok(epicDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/bulk")
    @Operation(summary = "Supprimer plusieurs Epics", description = "Supprime une liste d'Epics")
    public ResponseEntity<Void> deleteAllEpics(@RequestBody List<EpicDTO> epicDTOs) {
        try {
            epicService.deleteAllEpics(epicDTOs);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}