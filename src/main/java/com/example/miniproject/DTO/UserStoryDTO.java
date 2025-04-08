package com.example.miniproject.DTO;

import java.util.ArrayList;
import java.util.List;

public class UserStoryDTO {
    private int id;
    private String title;
    private String description;
    private String priorite; // "HAUTE", "MOYENNE", "BASSE"
    private String status; // "A_FAIRE", "EN_COURS", "REVUE", "TERMINEE"
    private int epicId;
    private int productBacklogId;
    private int sprintBacklogId;
    private List<Integer> taskIds = new ArrayList<>();
    private String acceptanceCriteria;

    private int complexity;


    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorite() {
        return priorite;
    }

    public void setPriorite(String priorite) {
        this.priorite = priorite;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public int getProductBacklogId() {
        return productBacklogId;
    }

    public void setProductBacklogId(int productBacklogId) {
        this.productBacklogId = productBacklogId;
    }

    public int getSprintBacklogId() {
        return sprintBacklogId;
    }

    public void setSprintBacklogId(int sprintBacklogId) {
        this.sprintBacklogId = sprintBacklogId;
    }

    public List<Integer> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Integer> taskIds) {
        this.taskIds = taskIds;
    }

    public String getAcceptanceCriteria() {
        return acceptanceCriteria;
    }

    public void setAcceptanceCriteria(String acceptanceCriteria) {
        this.acceptanceCriteria = acceptanceCriteria;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }
}