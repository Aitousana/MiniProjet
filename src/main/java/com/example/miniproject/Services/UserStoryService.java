package com.example.miniproject.Services;

import com.example.miniproject.entities.UserStory;

import java.util.List;

public interface UserStoryService {
    public void addUserStory(UserStory userStory);
    public void deleteUserStory(UserStory userStory);
    public void updateUserStory(UserStory userStory);
    public List<UserStory> getUserStory();
    public UserStory getUserStoryById(int id);

}
