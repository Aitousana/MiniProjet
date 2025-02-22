package com.example.miniproject.Services;

import com.example.miniproject.entities.UserStory;

public interface UserStoryService {
    public void addUserStory(UserStory userStory);
    public void deleteUserStory(UserStory userStory);
    public void updateUserStory(UserStory userStory);

}
