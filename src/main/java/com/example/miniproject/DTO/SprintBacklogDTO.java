package com.example.miniproject.DTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SprintBacklogDTO {
    private int id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int capacity;
    private List<Integer> userStoryIds = new ArrayList<>();
    private List<Integer> taskIds = new ArrayList<>();

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Integer> getUserStoryIds() { return userStoryIds; }
    public void setUserStoryIds(List<Integer> userStoryIds) { this.userStoryIds = userStoryIds; }
    public List<Integer> getTaskIds() { return taskIds; }
    public void setTaskIds(List<Integer> taskIds) { this.taskIds = taskIds; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
}