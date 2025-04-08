package com.example.miniproject.Services;

import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.entities.UserStory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserStoryService {
    void addUserStory(UserStoryDTO userStoryDTO);
    void deleteUserStory(UserStoryDTO userStoryDTO);
    void updateUserStory(UserStoryDTO userStoryDTO);
    List<UserStoryDTO> getUserStories();
    Page<UserStoryDTO> getUserStories(Pageable pageable);
    UserStoryDTO getUserStoryById(int id);
    void deleteAllUserStories(List<UserStoryDTO> userStoryDTOList);
    List<UserStoryDTO> getUserStoriesByEpicId(int epicId);
    UserStory getUserStoryEntityById(int id);

}