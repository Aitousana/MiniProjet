package com.example.miniproject.Services;

import com.example.miniproject.Repositories.UserStoryRepo;
import com.example.miniproject.entities.UserStory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserStoryServiceImpl implements UserStoryService {
    private final UserStoryRepo userStoryRepo;

    public UserStoryServiceImpl(UserStoryRepo userStoryRepo) {
        this.userStoryRepo = userStoryRepo;
    }

    @Override
    public void addUserStory(UserStory userStory) {
        userStoryRepo.save(userStory);
    }

    @Override
    public void deleteUserStory(UserStory userStory) {
        if (userStoryRepo.existsById(userStory.getId())) {
            userStoryRepo.delete(userStory);
        } else {
            throw new RuntimeException("Aucun UserStory trouvé avec l'ID " + userStory.getId());
        }
    }


    @Override
    public void updateUserStory(UserStory userStory) {
        if(userStoryRepo.existsById(userStory.getId())) {

            userStoryRepo.save(userStory);
        } else {
            throw new EntityNotFoundException("Aucune User Story trouvée avec cet ID.");
        }
    }
}
