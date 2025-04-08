package com.example.miniproject.Services;

import com.example.miniproject.DTO.SprintHistoryDTO;

import java.util.List;

public interface SprintHistoryService {
    void logSprintProgress(int sprintBacklogId);
    List<SprintHistoryDTO> getSprintHistory(int sprintBacklogId);
}