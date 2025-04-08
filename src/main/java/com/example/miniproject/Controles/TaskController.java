package com.example.miniproject.Controles;

import com.example.miniproject.DTO.TaskDTO;
import com.example.miniproject.Services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks", description = "API pour gérer les Tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Ajouter une Task", description = "Crée une nouvelle Task")
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO) {
        try {
            TaskDTO savedTask = taskService.addTask(taskDTO);
            return ResponseEntity.status(201).body(savedTask);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une Task", description = "Met à jour une Task existante")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable int id, @RequestBody TaskDTO taskDTO) {
        try {
            taskDTO.setId(id);
            TaskDTO updatedTask = taskService.updateTask(taskDTO);
            return ResponseEntity.ok(updatedTask);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une Task", description = "Supprime une Task par son ID")
    public ResponseEntity<Void> deleteTask(@PathVariable int id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Récupérer toutes les Tasks", description = "Retourne la liste de toutes les Tasks")
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une Task par ID", description = "Retourne les détails d'une Task spécifique")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable int id) {
        try {
            TaskDTO taskDTO = taskService.getTaskById(id);
            return ResponseEntity.ok(taskDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}