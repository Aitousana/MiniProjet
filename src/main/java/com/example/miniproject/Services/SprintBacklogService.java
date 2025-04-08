package com.example.miniproject.Services;

import com.example.miniproject.DTO.SprintBacklogDTO;
import com.example.miniproject.entities.SprintBacklog;

import java.util.List;

public interface SprintBacklogService {
    SprintBacklogDTO addSprintBacklog(SprintBacklogDTO sprintBacklogDTO);
    SprintBacklogDTO updateSprintBacklog(SprintBacklogDTO sprintBacklogDTO);
    void deleteSprintBacklog(int id);
    List<SprintBacklogDTO> getAllSprintBacklogs();
    SprintBacklogDTO getSprintBacklogById(int id);
    SprintBacklog getSprintBacklogEntityById(int id);
}