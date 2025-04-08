package com.example.miniproject.DTO;

public class TaskDTO {
    private int id;
    private String title;
    private String description;
    private String status; // "TO_DO", "IN_PROGRESS", "DONE"
    private int userStoryId;
    private int sprintBacklogId;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getUserStoryId() { return userStoryId; }
    public void setUserStoryId(int userStoryId) { this.userStoryId = userStoryId; }
    public int getSprintBacklogId() { return sprintBacklogId; }
    public void setSprintBacklogId(int sprintBacklogId) { this.sprintBacklogId = sprintBacklogId; }
}