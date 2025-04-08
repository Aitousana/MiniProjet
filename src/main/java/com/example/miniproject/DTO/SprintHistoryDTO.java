package com.example.miniproject.DTO;

import java.time.LocalDateTime;

public class SprintHistoryDTO {
    private int id;
    private int sprintBacklogId;
    private LocalDateTime timestamp;
    private int remainingWork;
    private String status;

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSprintBacklogId() { return sprintBacklogId; }
    public void setSprintBacklogId(int sprintBacklogId) { this.sprintBacklogId = sprintBacklogId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public int getRemainingWork() { return remainingWork; }
    public void setRemainingWork(int remainingWork) { this.remainingWork = remainingWork; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}