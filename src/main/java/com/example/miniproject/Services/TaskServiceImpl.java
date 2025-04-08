package com.example.miniproject.Services;

import com.example.miniproject.DTO.TaskDTO;
import com.example.miniproject.Repositories.TaskRepo;
import com.example.miniproject.entities.Task;
import com.example.miniproject.mapper.TaskMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final UserStoryService userStoryService;
    private final SprintBacklogService sprintBacklogService;

    public TaskServiceImpl(TaskRepo taskRepo, UserStoryService userStoryService, SprintBacklogService sprintBacklogService) {
        this.taskRepo = taskRepo;
        this.userStoryService = userStoryService;
        this.sprintBacklogService = sprintBacklogService;
    }

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) {
        Task task = TaskMapper.INSTANCE.toEntity(taskDTO);
        task.setUserStory(userStoryService.getUserStoryEntityById(taskDTO.getUserStoryId()));
        if (taskDTO.getSprintBacklogId() != 0) {
            task.setSprintBacklog(sprintBacklogService.getSprintBacklogEntityById(taskDTO.getSprintBacklogId()));
        }
        Task savedTask = taskRepo.save(task);
        return TaskMapper.INSTANCE.toDTO(savedTask);
    }

    @Override
    public void deleteTask(int id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task avec l'ID " + id + " non trouvé"));
        taskRepo.delete(task);
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepo.findAll().stream()
                .map(TaskMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(int id) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task avec l'ID " + id + " non trouvé"));
        return TaskMapper.INSTANCE.toDTO(task);
    }

    @Override
    public TaskDTO updateTask(TaskDTO taskDTO) {
        Task existingTask = taskRepo.findById(taskDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task avec l'ID " + taskDTO.getId() + " non trouvé"));

        if (!Objects.equals(existingTask.getStatus(), Task.Status.valueOf(taskDTO.getStatus()))) {
            if (!canMoveToStatus(existingTask.getStatus(), Task.Status.valueOf(taskDTO.getStatus()))) {
                throw new IllegalStateException("Transition de statut non autorisée : de " + existingTask.getStatus() + " à " + taskDTO.getStatus());
            }
            existingTask.setStatus(Task.Status.valueOf(taskDTO.getStatus()));
        }

        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setUserStory(userStoryService.getUserStoryEntityById(taskDTO.getUserStoryId()));
        if (taskDTO.getSprintBacklogId() != 0) {
            existingTask.setSprintBacklog(sprintBacklogService.getSprintBacklogEntityById(taskDTO.getSprintBacklogId()));
        } else {
            existingTask.setSprintBacklog(null);
        }
        Task updatedTask = taskRepo.save(existingTask);
        return TaskMapper.INSTANCE.toDTO(updatedTask);
    }

    private boolean canMoveToStatus(Task.Status current, Task.Status next) {
        if (current == Task.Status.DONE) return false;
        return switch (current) {
            case TO_DO -> next == Task.Status.IN_PROGRESS;
            case IN_PROGRESS -> next == Task.Status.DONE;
            default -> false;
        };
    }
}