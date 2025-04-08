package com.example.miniproject.Controles;

import com.example.miniproject.DTO.ProductBacklogDTO;
import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.Services.ProductBacklogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-backlogs")
@Tag(name = "Product Backlogs", description = "API pour gérer les Product Backlogs")
public class ProductBacklogController {

    private final ProductBacklogService productBacklogService;

    public ProductBacklogController(ProductBacklogService productBacklogService) {
        this.productBacklogService = productBacklogService;
    }

    @PostMapping
    @Operation(summary = "Ajouter un Product Backlog", description = "Crée un nouveau Product Backlog")
    public ResponseEntity<ProductBacklogDTO> addProductBacklog(@RequestBody ProductBacklogDTO backlogDTO) {
        ProductBacklogDTO savedBacklog = productBacklogService.addProductBacklog(backlogDTO);
        return ResponseEntity.status(201).body(savedBacklog);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un Product Backlog", description = "Met à jour un Product Backlog existant")
    public ResponseEntity<ProductBacklogDTO> updateProductBacklog(@PathVariable int id, @RequestBody ProductBacklogDTO backlogDTO) {
        try {
            backlogDTO.setId(id);
            ProductBacklogDTO updatedBacklog = productBacklogService.updateProductBacklog(backlogDTO);
            return ResponseEntity.ok(updatedBacklog);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un Product Backlog", description = "Supprime un Product Backlog par son ID")
    public ResponseEntity<Void> deleteProductBacklog(@PathVariable int id) {
        try {
            ProductBacklogDTO backlogDTO = new ProductBacklogDTO();
            backlogDTO.setId(id);
            productBacklogService.deleteProductBacklog(backlogDTO);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les Product Backlogs", description = "Retourne la liste de tous les Product Backlogs")
    public ResponseEntity<List<ProductBacklogDTO>> getAllProductBacklogs() {
        List<ProductBacklogDTO> backlogs = productBacklogService.getAllProductBacklogs();
        return ResponseEntity.ok(backlogs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un Product Backlog par ID", description = "Retourne les détails d'un Product Backlog spécifique")
    public ResponseEntity<ProductBacklogDTO> getProductBacklogById(@PathVariable int id) {
        return productBacklogService.getProductBacklogById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body(null));
    }

//    @GetMapping("/{id}/prioritize")
//    @Operation(summary = "Prioriser les User Stories d'un Product Backlog", description = "Trie les User Stories selon la méthode spécifiée")
//    public ResponseEntity<List<UserStoryDTO>> prioritizeUserStories(
//            @PathVariable int id,
//            @RequestParam String method) {
//        try {
//            List<UserStoryDTO> prioritized = productBacklogService.prioritizeProductBacklog(id, method);
//            return ResponseEntity.ok(prioritized);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(404).body(null);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(400).body(null);
//        }
//    }
}