package com.example.miniproject.Services;

import com.example.miniproject.DTO.SprintBacklogDTO;
import com.example.miniproject.Repositories.SprintBacklogRepo;
import com.example.miniproject.entities.SprintBacklog;
import com.example.miniproject.entities.UserStory;
import com.example.miniproject.mapper.SprintBacklogMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintBacklogServiceImpl implements SprintBacklogService {

    private final SprintBacklogRepo sprintBacklogRepo;

    public SprintBacklogServiceImpl(SprintBacklogRepo sprintBacklogRepo) {
        this.sprintBacklogRepo = sprintBacklogRepo;
    }
    @Override
    public SprintBacklogDTO addSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        if (sprintBacklogDTO.getStartDate().isAfter(sprintBacklogDTO.getEndDate())) {
            throw new IllegalArgumentException("La date de début doit être avant la date de fin");
        }

        // Mapper DTO vers Entity
        SprintBacklog sprintBacklog = SprintBacklogMapper.INSTANCE.toEntity(sprintBacklogDTO);

        // Enregistrer le SprintBacklog
        SprintBacklog savedSprint = sprintBacklogRepo.save(sprintBacklog);

        // Associer chaque UserStory au SprintBacklog nouvellement créé
        if (savedSprint.getUserStories() != null) {
            for (UserStory userStory : savedSprint.getUserStories()) {
                userStory.setSprintBacklog(savedSprint); // Liaison côté UserStory
            }
        }

        return SprintBacklogMapper.INSTANCE.toDTO(savedSprint);
    }

    @Override
    public SprintBacklogDTO updateSprintBacklog(SprintBacklogDTO sprintBacklogDTO) {
        SprintBacklog existingSprint = sprintBacklogRepo.findById(sprintBacklogDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog avec l'ID " + sprintBacklogDTO.getId() + " non trouvé"));
        if (sprintBacklogDTO.getStartDate().isAfter(sprintBacklogDTO.getEndDate())) {
            throw new IllegalArgumentException("La date de début doit être avant la date de fin");
        }
        existingSprint.setName(sprintBacklogDTO.getName());
        existingSprint.setStartDate(sprintBacklogDTO.getStartDate());
        existingSprint.setEndDate(sprintBacklogDTO.getEndDate());
        existingSprint.setCapacity(sprintBacklogDTO.getCapacity());
        SprintBacklog updatedSprint = sprintBacklogRepo.save(existingSprint);
        return SprintBacklogMapper.INSTANCE.toDTO(updatedSprint);
    }

    @Override
    public void deleteSprintBacklog(int id) {
        SprintBacklog sprintBacklog = sprintBacklogRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog avec l'ID " + id + " non trouvé"));
        sprintBacklogRepo.delete(sprintBacklog);
    }

    @Override
    public List<SprintBacklogDTO> getAllSprintBacklogs() {
        return sprintBacklogRepo.findAll().stream()
                .map(SprintBacklogMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SprintBacklogDTO getSprintBacklogById(int id) {
        SprintBacklog sprintBacklog = sprintBacklogRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog avec l'ID " + id + " non trouvé"));
        return SprintBacklogMapper.INSTANCE.toDTO(sprintBacklog);
    }

    @Override
    public SprintBacklog getSprintBacklogEntityById(int id) {
        return sprintBacklogRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SprintBacklog avec l'ID " + id + " non trouvé"));
    }
}