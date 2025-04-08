package com.example.miniproject.DTO;

import java.util.ArrayList;
import java.util.List;

public class EpicDTO {
    private int id;
    private String title;
    private String description;
    private int productBacklogId;
    private List<Integer> userStoryIds = new ArrayList<>();

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getProductBacklogId() { return productBacklogId; }
    public void setProductBacklogId(int productBacklogId) { this.productBacklogId = productBacklogId; }
    public List<Integer> getUserStoryIds() { return userStoryIds; }
    public void setUserStoryIds(List<Integer> userStoryIds) { this.userStoryIds = userStoryIds; }
}