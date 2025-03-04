package com.example.miniproject.Services;

import com.example.miniproject.Repositories.EpicRepo;
import com.example.miniproject.entities.Epic;
import com.example.miniproject.entities.UserStory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpicServiceImpl implements EpicService {

    EpicRepo epicRepo;
    UserStoryService userStoryService;
    public EpicServiceImpl(EpicRepo epicRepo, UserStoryService userStoryService) {
        this.epicRepo = epicRepo;
        this.userStoryService = userStoryService;
    }

    @Override
    public void AddEpic(Epic epic) {
        if(epic == null || epic.getProductBacklog() == null) {
            throw new EntityNotFoundException("Epic cannot be null");
        }
        epicRepo.save(epic);

    }

    @Override
    public void DeleteEpic(Epic epic) {
        if(!epicRepo.existsById(epic.getId())) {
            throw new EntityNotFoundException("Epic cannot be deleted");
        }
        List<UserStory> userStories=epic.getUserStories();
            userStoryService.delateAllUserStory(userStories);
            epicRepo.deleteById(epic.getId());


    }

    @Override
    public void UpdateEpic(Epic epic) {
        if(!epicRepo.existsById(epic.getId())) {
            throw new EntityNotFoundException("Epic cannot be updated");
        }
        epic.setTitle(epic.getTitle());
        epicRepo.save(epic);
    }

    @Override
    public List<Epic> getAllEpic() {

        return epicRepo.findAll();

    }

    @Override
    public Epic getEpicById(int id) {
        return epicRepo.findById(id).orElseThrow(NullPointerException::new);
    }

    @Override
    public void deleteAllEpic(List<Epic> epic) {
        if(epic.size()==0){
            throw new NullPointerException("aucun Epic Stockée");
        }
        for(Epic ep:epic){
          userStoryService.delateAllUserStory(ep.getUserStories());
          epicRepo.deleteById(ep.getId());
        }

    }


}
