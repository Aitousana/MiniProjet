package com.example.miniproject.DTO;

import java.util.ArrayList;
import java.util.List;

public class ProductBacklogDTO {
    private int id;
    private String title;
    private List<Integer> epicIds = new ArrayList<>();
    private List<Integer> userStoryIds = new ArrayList<>();

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<Integer> getEpicIds() { return epicIds; }
    public void setEpicIds(List<Integer> epicIds) { this.epicIds = epicIds; }
    public List<Integer> getUserStoryIds() { return userStoryIds; }
    public void setUserStoryIds(List<Integer> userStoryIds) { this.userStoryIds = userStoryIds; }
}