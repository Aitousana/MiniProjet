package com.example.miniproject.Controles;

import com.example.miniproject.Services.SprintBacklogService;
import com.example.miniproject.Services.UserStoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@Tag(name = "Dashboard", description = "API pour le tableau de bord")
public class DashboardController {

    private final SprintBacklogService sprintBacklogService;
    private final UserStoryService userStoryService;

    public DashboardController(SprintBacklogService sprintBacklogService, UserStoryService userStoryService) {
        this.sprintBacklogService = sprintBacklogService;
        this.userStoryService = userStoryService;
    }

    @GetMapping("/progress")
    @Operation(summary = "Récupérer les indicateurs de progression", description = "Retourne un tableau de bord avec les indicateurs")
    public ResponseEntity<Map<String, Object>> getProgress() {
        Map<String, Object> progress = new HashMap<>();
        progress.put("activeSprints", sprintBacklogService.getAllSprintBacklogs().size());
        progress.put("completedUserStories", userStoryService.getUserStories().stream()
                .filter(us -> us.getStatus().equals("TERMINEE")).count());
        progress.put("totalUserStories", userStoryService.getUserStories().size());
        return ResponseEntity.ok(progress);
    }
}