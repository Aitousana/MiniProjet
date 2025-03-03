package com.example.miniproject.Services;

import com.example.miniproject.Repositories.ProductBacklogRepo;
import com.example.miniproject.entities.ProductBacklog;
import com.example.miniproject.entities.UserStory;
import com.example.miniproject.entities.Epic;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBacklogServiceImpl implements ProductBaclogService {

    private final ProductBacklogRepo productBacklogRepo;
    private final UserStoryService userStoryService;
    //private final EpicService epicService;

    public ProductBacklogServiceImpl(ProductBacklogRepo productBacklogRepo, UserStoryService userStoryService) {
        this.productBacklogRepo = productBacklogRepo;
        this.userStoryService = userStoryService;
       // this.epicService = epicService;
    }

    @Override
    public ProductBacklog addProductBacklog(ProductBacklog backlog) {
        return productBacklogRepo.save(backlog);
    }

    @Override
    public List<ProductBacklog> getAllProductBacklogs() {
        return productBacklogRepo.findAll();
    }

    @Override
    public Optional<ProductBacklog> getProductBacklogById(int id) {
        return productBacklogRepo.findById(id);
    }

    @Override
    public ProductBacklog updateProductBacklog(int id, ProductBacklog backlog) {
        if (!productBacklogRepo.existsById(id)) {
            throw new EntityNotFoundException("ProductBacklog with id " + id + " not found");
        }
        backlog.setBacklogId(id);
        return productBacklogRepo.save(backlog);
    }

    @Override
    public void deleteProductBacklog(int id) {
        if (!productBacklogRepo.existsById(id)) {
            throw new EntityNotFoundException("ProductBacklog with id " + id + " not found");
        }
        productBacklogRepo.deleteById(id);
    }

    @Override
    public ProductBacklog addUserStoryToBacklog(int backlogId, UserStory userStory) {
        ProductBacklog backlog = productBacklogRepo.findById(backlogId)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog with id " + backlogId + " not found"));

        userStory.setProductBacklog(backlog);
        userStoryService.addUserStory(userStory);

        backlog.getUserStories().add(userStory);
        return productBacklogRepo.save(backlog);
    }

    @Override
    public ProductBacklog removeUserStoryFromBacklog(int backlogId, int userStoryId) {
        ProductBacklog backlog = productBacklogRepo.findById(backlogId)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog with id " + backlogId + " not found"));

        UserStory userStory = userStoryService.getUserStoryById(userStoryId);
                if(userStory==null) {throw new EntityNotFoundException("UserStory with id " + userStoryId + " not found");}

        backlog.getUserStories().remove(userStory);

        userStoryService.deleteUserStory(userStory);
        return productBacklogRepo.save(backlog);
    }

//    @Override
//    public ProductBacklog addEpicToBacklog(int backlogId, Epic epic) {
//        ProductBacklog backlog = productBacklogRepo.findById(backlogId)
//                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog with id " + backlogId + " not found"));
//
//        epic.setProductBacklog(backlog);
//        epicService.addEpic(epic);
//
//        backlog.getEpics().add(epic);
//        return productBacklogRepo.save(backlog);
//    }

//    @Override
//    public ProductBacklog removeEpicFromBacklog(int backlogId, int epicId) {
//        ProductBacklog backlog = productBacklogRepo.findById(backlogId)
//                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog with id " + backlogId + " not found"));
//
//        Epic epic = epicService.getEpicById(epicId)
//                .orElseThrow(() -> new EntityNotFoundException("Epic with id " + epicId + " not found"));
//
//        backlog.getEpics().remove(epic);
//        epicService.deleteEpic(epicId);
//        return productBacklogRepo.save(backlog);
//    }
}