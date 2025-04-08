package com.example.miniproject.Services;

import com.example.miniproject.Repositories.SprintHistoryRepo;
import com.example.miniproject.DTO.SprintHistoryDTO;
import com.example.miniproject.entities.SprintBacklog;
import com.example.miniproject.entities.SprintHistory;
import com.example.miniproject.entities.UserStory;
import com.example.miniproject.mapper.SprintHistoryMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintHistoryServiceImpl implements SprintHistoryService {

    private final SprintHistoryRepo sprintHistoryRepo;
    private final SprintBacklogService sprintBacklogService;

    public SprintHistoryServiceImpl(SprintHistoryRepo sprintHistoryRepo, SprintBacklogService sprintBacklogService) {
        this.sprintHistoryRepo = sprintHistoryRepo;
        this.sprintBacklogService = sprintBacklogService;
    }

    @Override
    public void logSprintProgress(int sprintBacklogId) {
        SprintBacklog sprint = sprintBacklogService.getSprintBacklogEntityById(sprintBacklogId);
        int remainingWork = sprint.getUserStories().stream()
                .filter(us -> !us.getStatut().equals(UserStory.Statut.TERMINEE))
                .mapToInt(UserStory::getComplexity)
                .sum();
        SprintHistory history = new SprintHistory();
        history.setSprintBacklog(sprint);
        history.setTimestamp(LocalDateTime.now());
        history.setRemainingWork(remainingWork);
        history.setStatus(sprint.getUserStories().stream().allMatch(us -> us.getStatut().equals(UserStory.Statut.TERMINEE)) ? "CLOSED" : "OPEN");
        sprintHistoryRepo.save(history);
    }

    @Override
    public List<SprintHistoryDTO> getSprintHistory(int sprintBacklogId) {
        return sprintHistoryRepo.findBySprintBacklogId(sprintBacklogId).stream()
                .map(SprintHistoryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}