package com.example.miniproject.Services;

import com.example.miniproject.DTO.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO addTask(TaskDTO taskDTO);
    TaskDTO updateTask(TaskDTO taskDTO);
    void deleteTask(int id);
    List<TaskDTO> getAllTasks();
    TaskDTO getTaskById(int id);
}